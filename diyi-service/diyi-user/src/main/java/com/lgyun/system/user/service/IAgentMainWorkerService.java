package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ChildAccountType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.dto.RoleMenusDTO;
import com.lgyun.system.user.dto.ChildAccountDTO;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
import com.lgyun.system.user.vo.AgentMainWorkerDetailVO;
import com.lgyun.system.user.vo.AgentMainWorkerInfoVO;
import com.lgyun.system.user.vo.AgentMainWorkerVO;
import com.lgyun.system.vo.RoleMenuInfoVO;
import com.lgyun.system.vo.RoleMenusVO;
import com.lgyun.system.vo.RolesVO;

import java.util.List;

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
     * 查询当前渠道商员工详情
     *
     * @param agentMainWorkerId
     * @return
     */
    R<AgentMainWorkerDetailVO> queryAgentMainWorkerDetail(Long agentMainWorkerId);

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

    /**
     * 创建或修改角色及菜单的分配
     *
     * @param agentMainWorkerEntity
     * @param roleMenusDTO
     * @return
     */
    R<String> createOrUpdateRoleMenus(AgentMainWorkerEntity agentMainWorkerEntity, RoleMenusDTO roleMenusDTO);

    /**
     * 查询当前商户人员的角色列表
     *
     * @param id
     * @return
     */
    R<List<RoleMenusVO>> queryRoleList(Long id);

    /**
     * 删除角色
     *
     * @param roleId
     * @return
     */
    R<String> removeRole(Long roleId);

    /**
     * 查询角色详情
     *
     * @param roleId
     * @return
     */
    R<RoleMenuInfoVO> queryRoleInfo(Long roleId);

    /**
     * 查询当前商户人员创建的角色(分配角色时显示的下拉框)
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
    R<List<AgentMainWorkerVO>> queryChildAccountList(Long id);

    /**
     * 查询商户人员的账号详情
     *
     * @param id
     * @param accountId
     * @return
     */
    R<AgentMainWorkerInfoVO> queryAccountDetail(Long id, Long accountId);

    /**
     * 创建或修改子账号及子账号的角色分配
     *
     * @param childAccountDTO
     * @param agentMainWorkerEntity
     * @return
     */
    R<String> createOrUpdateChildAccount(ChildAccountDTO childAccountDTO, AgentMainWorkerEntity agentMainWorkerEntity);

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

