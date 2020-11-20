package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;

/**
 * 渠道商员工表 Service 接口
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
public interface IAgentMainWorkerService extends BaseService<AgentMainWorkerEntity> {

    /**
     * 查询当前渠道商员工
     *
     * @param bladeUser
     * @return
     */
    R<AgentMainWorkerEntity> currentAgentMainWorker(BladeUser bladeUser);

    /**
     * 根据userId查询渠道商员工
     *
     * @param userId
     * @return
     */
    AgentMainWorkerEntity findByUserId(Long userId);
    
    /**
     * 根据手机号码查询渠道商员工是否存在
     *
     * @param phoneNumber
     * @return
     */
    int findCountByPhoneNumber(String phoneNumber);

    /**
     * 根据账号密码查询渠道商员工
     *
     * @param employeeUserName
     * @param password
     * @return
     */
    AgentMainWorkerEntity findByEmployeeUserNameAndEmployeePwd(String employeeUserName, String password);

    /**
     * 根据手机号码查询渠道商员工
     *
     * @param phoneNumber
     * @return
     */
    AgentMainWorkerEntity findByPhoneNumber(String phoneNumber);
}

