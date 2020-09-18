package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.BeanServiceUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.common.tool.RedisUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.feign.ISysClient;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.dto.enterprise.AddOrUpdateEnterpriseContactDto;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.mapper.EnterpriseWorkerMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IUserService;
import com.lgyun.system.user.vo.EnterpriseWorkerVO;
import com.lgyun.system.user.vo.admin.QueryEnterpriseWorkerListVO;
import com.lgyun.system.vo.GrantRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
@RequiredArgsConstructor
public class EnterpriseWorkerServiceImpl extends BaseServiceImpl<EnterpriseWorkerMapper, EnterpriseWorkerEntity> implements IEnterpriseWorkerService {

    private final IUserService userService;
    private final RedisUtil redisUtil;
    private final ISysClient sysClient;

    @Autowired
    @Lazy
    private IEnterpriseService enterpriseService;

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

    @Override
    public R<EnterpriseWorkerEntity> currentEnterpriseWorker(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("账号未登陆");
        }

        User user = userService.getById(bladeUser.getUserId());
        if (user == null) {
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> addOrUpdateEnterpriseContact(AddOrUpdateEnterpriseContactDto addOrUpdateEnterpriseContactDto, Long enterpriseWorkerId) {

        //判断商户联系人是否相同
        if (addOrUpdateEnterpriseContactDto.getContact1Phone().equals(addOrUpdateEnterpriseContactDto.getContact2Phone())) {
            return R.fail("联系人1电话/手机和联系人2电话/手机不能一致");
        }

        //判断商户联系人1是否已存在
        EnterpriseWorkerEntity oldEnterpriseWorkerEntity1 = findByPhoneNumber(addOrUpdateEnterpriseContactDto.getContact1Phone());
        User user1 = null;
        if (oldEnterpriseWorkerEntity1 != null) {

            if (!(oldEnterpriseWorkerEntity1.getEnterpriseId().equals(addOrUpdateEnterpriseContactDto.getEnterpriseId()))){
                return R.fail("联系人1电话/手机：" + addOrUpdateEnterpriseContactDto.getContact1Phone() + "已存在");
            }

            user1 = userService.findByPhone(addOrUpdateEnterpriseContactDto.getContact1Phone(), UserType.ENTERPRISE);
            if (user1 == null) {
                return R.fail("联系人1系统数据有误");
            }
        }

        //判断商户联系人2是否已存在
        EnterpriseWorkerEntity oldEnterpriseWorkerEntity2 = findByPhoneNumber(addOrUpdateEnterpriseContactDto.getContact2Phone());
        User user2 = null;
        if (oldEnterpriseWorkerEntity2 != null) {

            if (!(oldEnterpriseWorkerEntity2.getEnterpriseId().equals(addOrUpdateEnterpriseContactDto.getEnterpriseId()))){
                return R.fail("联系人2电话/手机：" + addOrUpdateEnterpriseContactDto.getContact2Phone() + "已存在");
            }

            user2 = userService.findByPhone(addOrUpdateEnterpriseContactDto.getContact2Phone(), UserType.ENTERPRISE);
            if (user2 == null) {
                return R.fail("联系人2系统数据有误");
            }
        }

        //处理联系人1
        if (oldEnterpriseWorkerEntity1 != null) {
            //修改联系人2
            user1.setName(addOrUpdateEnterpriseContactDto.getContact1Name());
            user1.setRealName(addOrUpdateEnterpriseContactDto.getContact1Name());
            user1.setEmail(addOrUpdateEnterpriseContactDto.getContact1Mail());
            userService.updateById(user1);

            oldEnterpriseWorkerEntity1.setWorkerName(addOrUpdateEnterpriseContactDto.getContact1Name());
            oldEnterpriseWorkerEntity1.setPositionName(addOrUpdateEnterpriseContactDto.getContact1Position());
            updateById(oldEnterpriseWorkerEntity1);
        } else {
            //新建联系人1
            user1 = new User();
            user1.setUserType(UserType.ENTERPRISE);
            user1.setAccount(addOrUpdateEnterpriseContactDto.getContact1Phone());
            user1.setPassword(DigestUtil.encrypt(String.valueOf(UUID.randomUUID())));
            user1.setName(addOrUpdateEnterpriseContactDto.getContact1Name());
            user1.setRealName(addOrUpdateEnterpriseContactDto.getContact1Name());
            user1.setEmail(addOrUpdateEnterpriseContactDto.getContact1Mail());
            user1.setPhone(addOrUpdateEnterpriseContactDto.getContact1Phone());
            userService.save(user1);

            oldEnterpriseWorkerEntity1 = new EnterpriseWorkerEntity();
            oldEnterpriseWorkerEntity1.setEnterpriseId(addOrUpdateEnterpriseContactDto.getEnterpriseId());
            oldEnterpriseWorkerEntity1.setUserId(user1.getId());
            oldEnterpriseWorkerEntity1.setWorkerName(addOrUpdateEnterpriseContactDto.getContact1Name());
            oldEnterpriseWorkerEntity1.setPositionName(addOrUpdateEnterpriseContactDto.getContact1Position());
            oldEnterpriseWorkerEntity1.setPhoneNumber(addOrUpdateEnterpriseContactDto.getContact1Phone());
            oldEnterpriseWorkerEntity1.setUpLevelId(enterpriseWorkerId);
            oldEnterpriseWorkerEntity1.setEmployeeUserName(addOrUpdateEnterpriseContactDto.getContact1Phone());
            oldEnterpriseWorkerEntity1.setEmployeePwd(DigestUtil.encrypt("123456"));
            oldEnterpriseWorkerEntity1.setAdminPower(true);
            save(oldEnterpriseWorkerEntity1);
        }

        //处理联系人2
        if (oldEnterpriseWorkerEntity2 != null) {
            //修改联系人2
            user2.setName(addOrUpdateEnterpriseContactDto.getContact2Name());
            user2.setRealName(addOrUpdateEnterpriseContactDto.getContact2Name());
            user2.setEmail(addOrUpdateEnterpriseContactDto.getContact2Mail());
            userService.updateById(user2);

            oldEnterpriseWorkerEntity2.setWorkerName(addOrUpdateEnterpriseContactDto.getContact2Name());
            oldEnterpriseWorkerEntity2.setPositionName(addOrUpdateEnterpriseContactDto.getContact2Position());
            updateById(oldEnterpriseWorkerEntity2);
        } else {
            //新建联系人2
            user2 = new User();
            user2.setUserType(UserType.ENTERPRISE);
            user2.setAccount(addOrUpdateEnterpriseContactDto.getContact2Phone());
            user2.setPassword(DigestUtil.encrypt(String.valueOf(UUID.randomUUID())));
            user2.setName(addOrUpdateEnterpriseContactDto.getContact2Name());
            user2.setRealName(addOrUpdateEnterpriseContactDto.getContact2Name());
            user2.setEmail(addOrUpdateEnterpriseContactDto.getContact2Mail());
            user2.setPhone(addOrUpdateEnterpriseContactDto.getContact2Phone());
            userService.save(user2);

            oldEnterpriseWorkerEntity2 = new EnterpriseWorkerEntity();
            oldEnterpriseWorkerEntity2.setEnterpriseId(addOrUpdateEnterpriseContactDto.getEnterpriseId());
            oldEnterpriseWorkerEntity2.setUserId(user2.getId());
            oldEnterpriseWorkerEntity2.setWorkerName(addOrUpdateEnterpriseContactDto.getContact2Name());
            oldEnterpriseWorkerEntity2.setPositionName(addOrUpdateEnterpriseContactDto.getContact2Position());
            oldEnterpriseWorkerEntity2.setPhoneNumber(addOrUpdateEnterpriseContactDto.getContact2Phone());
            oldEnterpriseWorkerEntity2.setUpLevelId(enterpriseWorkerId);
            oldEnterpriseWorkerEntity2.setEmployeeUserName(addOrUpdateEnterpriseContactDto.getContact2Phone());
            oldEnterpriseWorkerEntity2.setEmployeePwd(DigestUtil.encrypt("123456"));
            oldEnterpriseWorkerEntity2.setAdminPower(true);
            save(oldEnterpriseWorkerEntity2);
        }

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

    @Override
    public R<List<QueryEnterpriseWorkerListVO>> queryEnterpriseWorkerList(Long enterpriseId, PositionName positionName) {
        return R.data(baseMapper.queryEnterpriseWorkerList(enterpriseId, positionName));
    }

}
