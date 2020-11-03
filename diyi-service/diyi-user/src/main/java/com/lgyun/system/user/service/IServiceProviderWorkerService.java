package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.vo.ServiceProviderWorkerDetailVO;
import com.lgyun.system.user.vo.ServiceProviderWorkerVO;

/**
 * 服务商员工表 Service 接口
 *
 * @author tzq
 * @since 2020-08-13 17:05:17
 */
public interface IServiceProviderWorkerService extends BaseService<ServiceProviderWorkerEntity> {

    /**
     * 查询当前服务商员工
     *
     * @param bladeUser
     * @return
     */
    R<ServiceProviderWorkerEntity> currentServiceProviderWorker(BladeUser bladeUser);

    /**
     * 查询当前服务商员工详情
     *
     * @param id
     * @return
     */
    R<ServiceProviderWorkerDetailVO> queryServiceProviderWorkerDetail(Long id);

    /**
     * 根据手机号码查询服务商员工
     *
     * @param phoneNumber
     * @return
     */
    ServiceProviderWorkerEntity findByPhoneNumber(String phoneNumber);

    /**
     * 根据用户名密码查询服务商员工
     *
     * @param
     * @return
     */
    ServiceProviderWorkerEntity findByEmployeeUserNameAndEmployeePwd(String employeeUserName, String employeePwd);

    /**
     * 根据手机号码查询服务商员工是否存在
     *
     * @param phoneNumber
     * @return
     */
    int findCountByPhoneNumber(String phoneNumber);

    /**
     * 根据userId查询服务商员工
     *
     * @param userId
     * @return
     */
    ServiceProviderWorkerEntity findByUserId(Long userId);

    /**
     * 新增或更新服务商账号信息，含认证授权
     *
     * @param request
     * @param workerEntity
     * @param bladeUser
     * @return
     */
    R<String> saveServiceProviderAccount(ServiceProviderWorkerVO request, ServiceProviderWorkerEntity workerEntity, BladeUser bladeUser);

}

