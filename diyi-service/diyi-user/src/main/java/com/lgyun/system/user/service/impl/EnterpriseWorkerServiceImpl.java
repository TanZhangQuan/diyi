package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.common.tool.RedisUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.mapper.EnterpriseWorkerMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IUserService;
import com.lgyun.system.user.vo.enterprise.EnterpriseContactRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

        //获取缓存短信验证码
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

}
