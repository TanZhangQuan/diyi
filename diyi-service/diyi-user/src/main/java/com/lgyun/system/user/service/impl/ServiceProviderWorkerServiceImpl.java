package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.common.tool.RedisUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.mapper.ServiceProviderWorkerMapper;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import com.lgyun.system.user.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 服务商员工表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-13 17:05:17
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderWorkerServiceImpl extends BaseServiceImpl<ServiceProviderWorkerMapper, ServiceProviderWorkerEntity> implements IServiceProviderWorkerService {

    private IUserService userService;
    private IServiceProviderService iServiceProviderService;
    private RedisUtil redisUtil;
    
    @Override
    public ServiceProviderWorkerEntity findByPhoneNumber(String phoneNumber) {
        QueryWrapper<ServiceProviderWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderWorkerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public ServiceProviderWorkerEntity findByEmployeeUserNameAndEmployeePwd(String employeeUserName, String employeePwd) {
        QueryWrapper<ServiceProviderWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderWorkerEntity::getEmployeeUserName, employeeUserName)
                .eq(ServiceProviderWorkerEntity::getEmployeePwd, employeePwd);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<ServiceProviderWorkerEntity> currentServiceProviderWorker(BladeUser bladeUser) {
        
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

        ServiceProviderWorkerEntity serviceProviderWorkerEntity = findByUserId(bladeUser.getUserId());
        if (serviceProviderWorkerEntity == null) {
            return R.fail("账号未注册");
        }

        ServiceProviderEntity serviceProviderEntity = iServiceProviderService.getById(serviceProviderWorkerEntity.getServiceProviderId());
        if (serviceProviderEntity == null) {
            return R.fail("服务商不存在");
        }

        if (!(AccountState.NORMAL.equals(serviceProviderEntity.getServiceProviderState()))) {
            return R.fail("服务商状态非正常，请联系客服");
        }

        if (!(AccountState.NORMAL.equals(serviceProviderWorkerEntity.getServiceProviderWorkerState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        return R.data(serviceProviderWorkerEntity);
    }

    @Override
    public ServiceProviderWorkerEntity findByUserId(Long userId) {
        QueryWrapper<ServiceProviderWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderWorkerEntity::getUserId, userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<String> updatePassword(UpdatePasswordDto updatePasswordDto) {

        ServiceProviderWorkerEntity serviceProviderWorkerEntity = findByPhoneNumber(updatePasswordDto.getPhoneNumber());
        if (serviceProviderWorkerEntity == null) {
            return R.fail("手机号未注册");
        }

        //获取缓存短信验证码
        String redisCode = (String) redisUtil.get(SmsConstant.AVAILABLE_TIME + updatePasswordDto.getPhoneNumber());
        //判断验证码
        if (!StringUtil.equalsIgnoreCase(redisCode, updatePasswordDto.getSmsCode())) {
            return R.fail("短信验证码不正确");
        }

        serviceProviderWorkerEntity.setEmployeePwd(DigestUtil.encrypt(updatePasswordDto.getNewPassword()));
        save(serviceProviderWorkerEntity);

        //删除缓存短信验证码
        redisUtil.del(SmsConstant.AVAILABLE_TIME + updatePasswordDto.getPhoneNumber());

        return R.success("修改密码成功");
    }

}
