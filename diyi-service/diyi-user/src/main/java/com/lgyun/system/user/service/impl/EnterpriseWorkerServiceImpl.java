package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.*;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.feign.ISysClient;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.mapper.EnterpriseWorkerMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IUserService;
import com.lgyun.system.user.vo.EnterpriseWorkerVO;
import com.lgyun.system.user.vo.enterprise.EnterpriseContactRequest;
import com.lgyun.system.vo.GrantRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
@Slf4j
@Service
@AllArgsConstructor
public class EnterpriseWorkerServiceImpl extends BaseServiceImpl<EnterpriseWorkerMapper, EnterpriseWorkerEntity> implements IEnterpriseWorkerService {

    private IEnterpriseService enterpriseService;
    private IUserService userService;
    private RedisUtil redisUtil;
    private ISysClient sysClient;

    @Override
    public EnterpriseWorkerEntity findByPhoneNumber(String phoneNumber) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public EnterpriseWorkerEntity findByEmployeeUserNameAndEmployeePwd(String employeeUserName, String employeePwd) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getEmployeeUserName, employeeUserName)
                .eq(EnterpriseWorkerEntity::getEmployeePwd, employeePwd);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 根据手机号密码查询商户员工
     *
     * @param phone
     * @param employeePwd
     * @return
     */
    @Override
    public EnterpriseWorkerEntity findByPhoneAndEmployeePwd(String phone, String employeePwd) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getPhoneNumber, phone)
                .eq(EnterpriseWorkerEntity::getEmployeePwd, employeePwd);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<EnterpriseWorkerEntity> currentEnterpriseWorker(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("账号未登陆");
        }

        User user = userService.getById(bladeUser.getUserId());
        if (user == null){
            return R.fail("用户不存在");
        }

        if (!(UserType.ENTERPRISE.equals(user.getUserType()))) {
            return R.fail("用户类型有误");
        }

        EnterpriseWorkerEntity enterpriseWorkerEntity = findByUserId(bladeUser.getUserId());
        if (enterpriseWorkerEntity == null) {
            return R.fail("账号未注册");
        }

        EnterpriseEntity enterpriseEntity = enterpriseService.getById(enterpriseWorkerEntity.getEnterpriseId());
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        if (!(AccountState.NORMAL.equals(enterpriseEntity.getEnterpriseState()))) {
            return R.fail("商户状态非正常，请联系客服");
        }

        if (!(AccountState.NORMAL.equals(enterpriseWorkerEntity.getEnterpriseWorkerState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        return R.data(enterpriseWorkerEntity);
    }

    @Override
    public EnterpriseWorkerEntity findByUserId(Long userId) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getUserId, userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<String> updatePassword(UpdatePasswordDto updatePasswordDto) {

        EnterpriseWorkerEntity enterpriseWorkerEntity = findByPhoneNumber(updatePasswordDto.getPhoneNumber());
        if (enterpriseWorkerEntity == null) {
            return R.fail("手机号未注册");
        }

        //查询缓存短信验证码
        String redisCode = (String) redisUtil.get(SmsConstant.AVAILABLE_TIME + updatePasswordDto.getPhoneNumber());
        //判断验证码
        if (!StringUtil.equalsIgnoreCase(redisCode, updatePasswordDto.getSmsCode())) {
            return R.fail("短信验证码不正确");
        }

        enterpriseWorkerEntity.setEmployeePwd(DigestUtil.encrypt(updatePasswordDto.getNewPassword()));
        save(enterpriseWorkerEntity);

        //删除缓存短信验证码
        redisUtil.del(SmsConstant.AVAILABLE_TIME + updatePasswordDto.getPhoneNumber());

        return R.success("修改密码成功");
    }

    /**
     * 新增商户联系人
     *
     * @param request
     * @return
     */
    @Override
    public R<String> addNewEnterpriseWorker(EnterpriseContactRequest request, EnterpriseWorkerEntity enterpriseWorkerEntity, BladeUser bladeUser) {
        //新建管理员
        User user = new User();
        user.setUserType(UserType.ENTERPRISE);
        user.setAccount(request.getPhoneNumber());
        user.setPassword(DigestUtil.encrypt(String.valueOf(UUID.randomUUID())));
        user.setPhone(request.getPhoneNumber());
        user.setEmail(request.getEmail());
        user.setName(request.getWorkerName());
        userService.save(user);

        EnterpriseWorkerEntity workerEntity = new EnterpriseWorkerEntity();
        BeanUtils.copyProperties(request, workerEntity);
        workerEntity.setEnterpriseId(enterpriseWorkerEntity.getEnterpriseId());
        workerEntity.setUserId(user.getId());
        workerEntity.setCreateUser(bladeUser.getUserId());
        workerEntity.setEnterpriseWorkerState(AccountState.NORMAL);
        workerEntity.setUpLevelId(enterpriseWorkerEntity.getId());
        // TODO 密码待修改
        workerEntity.setEmployeePwd(DigestUtil.encrypt(String.valueOf(UUID.randomUUID())));
        this.save(workerEntity);

        return R.success("创建成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> saveEnterpriseAccount(EnterpriseWorkerVO request, EnterpriseWorkerEntity enterpriseWorkerEntity, BladeUser bladeUser) {
        User userLogin = userService.getById(bladeUser.getUserId());
        if (request.getId() != null) {
            // 更新账号
            EnterpriseWorkerEntity entity = this.getById(request.getId());
            if (entity == null) {
                return R.fail("没有此账号");
            }

            BeanUtils.copyProperties(request, entity, BeanServiceUtil.getNullPropertyNames(request));
            if (request.getMenuNameList() != null && request.getMenuNameList().size() > 0) {
                String collect = request.getMenuNameList().stream().collect(Collectors.joining(", "));
                entity.setMenus(collect);
            }
            if (StringUtils.isNotBlank(request.getEmployeePwd())) {
                String encrypt = DigestUtil.encrypt(request.getEmployeePwd());
                entity.setEmployeePwd(encrypt);
                userLogin.setPassword(encrypt);
                userLogin.setAccount(entity.getPhoneNumber());
            }

            userService.updateById(userLogin);
            this.updateById(entity);
        } else {
            // 新增账号
            EnterpriseWorkerEntity workerServiceByPhoneNumber = this.findByPhoneNumber(request.getPhoneNumber());
            if (workerServiceByPhoneNumber != null) {
                return R.fail("该手机号已经注册过");
            }

            UserInfo userInfo = userService.userInfoByPhone(request.getPhoneNumber(), UserType.ENTERPRISE);
            if (userInfo != null) {
                return R.fail("该手机号已经注册过");
            }
            //新建管理员
            User user = new User();
            user.setUserType(UserType.ENTERPRISE);
            user.setAccount(request.getPhoneNumber());
            if (StringUtils.isNotBlank(request.getEmployeePwd())) {
                user.setPassword(DigestUtil.encrypt(request.getEmployeePwd()));
            }
            user.setPhone(request.getPhoneNumber());
            user.setName(request.getEmployeeUserName());
            user.setRealName(request.getWorkerName());
            userService.save(user);

            EnterpriseWorkerEntity entity = new EnterpriseWorkerEntity();
            BeanUtils.copyProperties(request, entity, BeanServiceUtil.getNullPropertyNames(request));
            if (request.getMenuNameList() != null && request.getMenuNameList().size() > 0) {
                String collect = request.getMenuNameList().stream().collect(Collectors.joining(", "));
                entity.setMenus(collect);
            }
            entity.setUserId(user.getId());
            entity.setEnterpriseId(enterpriseWorkerEntity.getEnterpriseId());
            entity.setUpLevelId(enterpriseWorkerEntity.getId());
            if (StringUtils.isNotBlank(request.getEmployeePwd())) {
                entity.setEmployeePwd(DigestUtil.encrypt(request.getEmployeePwd()));
            }

            this.save(entity);
            request.setId(entity.getId());
        }
        if (request.getMenuIds() != null && request.getMenuIds().size() > 0) {
            GrantRequest grantRequest = new GrantRequest();
            grantRequest.setAccountId(request.getId());
            List<String> menuIds = request.getMenuIds();
            List<Long> menuList = new ArrayList<>();
            menuIds.stream().forEach(menu -> {
                menuList.add(Long.valueOf(menu));
            });
            grantRequest.setMenuIds(menuList);

            grantRequest.setUserId(bladeUser.getUserId());

            R grant = sysClient.grantFeign(grantRequest);
            if (!grant.isSuccess()) {
                return R.fail("新增、更新商户账号失败");
            }
        }

        return R.success("OK");
    }

}
