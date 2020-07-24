package com.lgyun.system.user.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;

/**
 * Service 接口
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
public interface IEnterpriseWorkerService extends BaseService<EnterpriseWorkerEntity> {

    /**
     * 根据手机号码获取商户员工
     *
     * @param phoneNumber
     * @return
     */
    EnterpriseWorkerEntity findByPhoneNumber(String phoneNumber);

    /**
     * 根据用户名密码获取商户员工
     *
     * @param
     * @return
     */
    EnterpriseWorkerEntity findByEmployeeUserNameAndEmployeePwd(String employeeUserName, String employeePwd);

}

