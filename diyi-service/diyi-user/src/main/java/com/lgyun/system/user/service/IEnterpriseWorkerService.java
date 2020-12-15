package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ChildAccountType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.dto.RoleMenusDTO;
import com.lgyun.system.user.dto.ChildAccountDTO;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.vo.EnterpriseWorkerDetailVO;
import com.lgyun.system.user.vo.EnterpriseWorkerInfoVO;
import com.lgyun.system.user.vo.EnterpriseWorkerVO;
import com.lgyun.system.vo.RoleMenuInfoVO;
import com.lgyun.system.vo.RoleMenusVO;
import com.lgyun.system.vo.RolesVO;

import java.util.List;

/**
 * 商户员工(联系人) Service 接口
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
public interface IEnterpriseWorkerService extends BaseService<EnterpriseWorkerEntity> {

    /**
     * 查询当前商户员工
     *
     * @param bladeUser
     * @return
     */
    R<EnterpriseWorkerEntity> currentEnterpriseWorker(BladeUser bladeUser);

    /**
     * 查询当前商户员工详情
     *
     * @param enterpriseWorkerId
     * @return
     */
    R<EnterpriseWorkerDetailVO> queryEnterpriseWorkerDetail(Long enterpriseWorkerId);

    /**
     * 根据手机号码查询商户员工
     *
     * @param phoneNumber
     * @return
     */
    EnterpriseWorkerEntity findByPhoneNumber(String phoneNumber);

    /**
     * 根据手机号/用户名，密码查询商户员工
     *
     * @param account
     * @param employeePwd
     * @return
     */
    EnterpriseWorkerEntity findByAccountAndPwd(String account, String employeePwd);

    /**
     * 根据手机号码查询商户员工是否存在
     *
     * @param phoneNumber
     * @return
     */
    int findCountByPhoneNumber(String phoneNumber);

    /**
     * 创建或修改角色及角色拥有的权限
     *
     * @param roleMenusDTO
     * @param enterpriseWorkerEntity
     * @return
     */
    R<String> createOrUpdateRoleMenus(EnterpriseWorkerEntity enterpriseWorkerEntity, RoleMenusDTO roleMenusDTO);

    /**
     * 查询商户人员所创建的角色
     *
     * @param id
     * @return
     */
    R<List<RoleMenusVO>> queryRoleList(Long id);

    /**
     * 查询角色的详情
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
    R<List<EnterpriseWorkerVO>> queryChildAccountList(Long id);

    /**
     * 查询商户人员的账号详情
     *
     * @param id
     * @param accountId
     * @return
     */
    R<EnterpriseWorkerInfoVO> queryAccountDetail(Long id, Long accountId);

    /**
     * 创建或修改子账号及子账号的角色分配
     *
     * @param childAccountDTO
     * @param enterpriseWorkerEntity
     * @return
     */
    R<String> createOrUpdateChildAccount(ChildAccountDTO childAccountDTO, EnterpriseWorkerEntity enterpriseWorkerEntity);

    /**
     * 删除、停用、启用子账号
     * @param childAccountId
     * @param childAccountType
     * @param id
     * @return
     */
    R<String> operateChildAccount(Long childAccountId, ChildAccountType childAccountType, Long id);
}


