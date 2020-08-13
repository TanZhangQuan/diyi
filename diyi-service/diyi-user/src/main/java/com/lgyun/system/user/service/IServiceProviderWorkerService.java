package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;

/**
 * 服务商员工表 Service 接口
 *
 * @author liangfeihu
 * @since 2020-08-13 17:05:17
 */
public interface IServiceProviderWorkerService extends BaseService<ServiceProviderWorkerEntity> {

    /**
     * 根据手机号码获取服务商员工
     *
     * @param phoneNumber
     * @return
     */
    ServiceProviderWorkerEntity findByPhoneNumber(String phoneNumber);

    /**
     * 根据用户名密码获取服务商员工
     *
     * @param
     * @return
     */
    ServiceProviderWorkerEntity findByEmployeeUserNameAndEmployeePwd(String employeeUserName, String employeePwd);

    /**
     * 获取当前服务商员工
     *
     * @param bladeUser
     * @return
     */
    R<ServiceProviderWorkerEntity> currentServiceProviderWorker(BladeUser bladeUser);

    /**
     * 根据userId查询服务商员工
     *
     * @param userId
     * @return
     */
    ServiceProviderWorkerEntity findByUserId(Long userId);

    /**
     * 修改密码
     *
     * @param updatePasswordDto
     * @return
     */
    R<String> updatePassword(UpdatePasswordDto updatePasswordDto);
}

