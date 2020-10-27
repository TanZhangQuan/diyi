package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.AddOrUpdateServiceProviderContactDTO;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.vo.ServiceProviderWorkerVO;
import com.lgyun.system.user.vo.ServiceProviderWorkerListVO;
import com.lgyun.system.user.vo.ServiceProviderWorkerDetailVO;

import java.util.List;

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
    Integer findCountByPhoneNumber(String phoneNumber);

    /**
     * 根据userId查询服务商员工
     *
     * @param userId
     * @return
     */
    ServiceProviderWorkerEntity findByUserId(Long userId);

    /**
     * 添加或修改服务商联系人
     *
     * @param addOrUpdateServiceProviderContactDto
     * @param serviceProviderWorkerId
     * @return
     */
    R<String> addOrUpdateServiceProviderContact(AddOrUpdateServiceProviderContactDTO addOrUpdateServiceProviderContactDto, Long serviceProviderWorkerId);

    /**
     * 查询服务商员工
     *
     * @param serviceProviderId
     * @param positionName
     * @return
     */
    R<List<ServiceProviderWorkerListVO>> queryServiceProviderWorkerList(Long serviceProviderId, PositionName positionName);

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

