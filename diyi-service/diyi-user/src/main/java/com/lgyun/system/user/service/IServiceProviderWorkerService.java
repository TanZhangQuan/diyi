package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.dto.serviceProvider.AddOrUpdateServiceProviderContactDto;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.vo.admin.QueryServiceProviderWorkerListVO;

import java.util.List;

/**
 * 服务商员工表 Service 接口
 *
 * @author tzq
 * @since 2020-08-13 17:05:17
 */
public interface IServiceProviderWorkerService extends BaseService<ServiceProviderWorkerEntity> {

    /**
     * 根据手机号码查询服务商员工
     *
     * @param phoneNumber
     * @return
     */
    ServiceProviderWorkerEntity findByPhoneNumber(String phoneNumber);

    /**
     * 根据手机号码查询服务商员工是否存在
     *
     * @param phoneNumber
     * @return
     */
    Integer findCountByPhoneNumber(String phoneNumber);

    /**
     * 根据用户名密码查询服务商员工是否存在
     *
     * @param
     * @return
     */
    Integer findCountByEmployeeUserNameAndEmployeePwd(String employeeUserName, String employeePwd);

    /**
     * 查询当前服务商员工
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

    /**
     * 添加或修改服务商联系人
     *
     * @param addOrUpdateServiceProviderContactDto
     * @param serviceProviderWorkerId
     * @return
     */
    R<String> addOrUpdateServiceProviderContact(AddOrUpdateServiceProviderContactDto addOrUpdateServiceProviderContactDto, Long serviceProviderWorkerId);

    /**
     * 查询服务商员工
     *
     * @param serviceProviderId
     * @param positionName
     * @return
     */
    R<List<QueryServiceProviderWorkerListVO>> queryServiceProviderWorkerList(Long serviceProviderId, PositionName positionName);
}

