package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ChildAccountType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.dto.RoleMenusDTO;
import com.lgyun.system.user.dto.ChildAccountDTO;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.vo.ServiceProviderWorkerDetailVO;
import com.lgyun.system.user.vo.ServiceProviderWorkerInfoVO;
import com.lgyun.system.user.vo.ServiceProviderWorkerVO;
import com.lgyun.system.vo.RoleMenuInfoVO;
import com.lgyun.system.vo.RoleMenusVO;
import com.lgyun.system.vo.RolesVO;

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
     * @param serviceProviderWorkerId
     * @return
     */
    R<ServiceProviderWorkerDetailVO> queryServiceProviderWorkerDetail(Long serviceProviderWorkerId);

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
     * 创建或修改角色及角色拥有的权限
     *
     * @param roleMenusDTO
     * @param serviceProviderWorkerEntity
     * @return
     */
    R<String> createOrUpdateRoleMenus(ServiceProviderWorkerEntity serviceProviderWorkerEntity, RoleMenusDTO roleMenusDTO);

    /**
     * 查询商户人员所创建的角色
     *
     * @param id
     * @return
     */
    R<List<RoleMenusVO>> queryRoleList(Long id);

    /**
     * 查询角色的详情
     *
     * @param roleId
     * @return
     */
    R<RoleMenuInfoVO> queryRoleInfo(Long roleId);

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    R<String> removeRole(Long roleId);

    /**
     * 查询当前管理人员所创建的角色
     *
     * @param id
     * @return
     */
    R<List<RolesVO>> queryRole(Long id);

    /**
     * 查询当前商户人员的所有主子账号
     *
     * @param id
     * @return
     */
    R<List<ServiceProviderWorkerVO>> queryChildAccountList(Long id);

    /**
     * 查询商户人员的账号详情
     *
     * @param id
     * @param accountId
     * @return
     */
    R<ServiceProviderWorkerInfoVO> queryAccountDetail(Long id, Long accountId);

    /**
     * 创建或修改子账号及子账号的角色分配
     *
     * @param childAccountDTO
     * @param serviceProviderWorkerEntity
     * @return
     */
    R<String> createOrUpdateChildAccount(ChildAccountDTO childAccountDTO, ServiceProviderWorkerEntity serviceProviderWorkerEntity);

    /**
     * 删除、停用、启用子账号
     *
     * @param childAccountId
     * @param childAccountType
     * @param id
     * @return
     */
    R<String> operateChildAccount(Long childAccountId, ChildAccountType childAccountType, Long id);

}

