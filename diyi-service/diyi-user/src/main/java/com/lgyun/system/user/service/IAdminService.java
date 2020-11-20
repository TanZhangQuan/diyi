package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ChildAccountType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.dto.RoleMenusDTO;
import com.lgyun.system.user.dto.ChildAccountDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.vo.AdminDetailVO;
import com.lgyun.system.user.vo.AdminInfoVO;
import com.lgyun.system.user.vo.AdminVO;
import com.lgyun.system.vo.RoleMenuInfoVO;
import com.lgyun.system.vo.RoleMenusVO;
import com.lgyun.system.vo.RolesVO;

import java.util.List;

/**
 * 平台管理员信息表 Service 接口
 *
 * @author tzq
 * @since 2020-09-19 15:02:12
 */
public interface IAdminService extends BaseService<AdminEntity> {

    /**
     * 查询当前管理员
     *
     * @param bladeUser
     * @return
     */
    R<AdminEntity> currentAdmin(BladeUser bladeUser);

    /**
     * 查询当前管理员详情
     *
     * @param adminId
     * @return
     */
    R<AdminDetailVO> queryAdminDetail(Long adminId);

    /**
     * 根据userId查询管理员
     *
     * @param userId
     * @return
     */
    AdminEntity findByUserId(Long userId);

    /**
     * 根据手机号查询管理员是否存在
     *
     * @param phoneNumber
     * @return
     */
    int findCountByPhoneNumber(String phoneNumber);

    /**
     * 根据手机号查询管理员
     *
     * @param phoneNumber
     * @return
     */
    AdminEntity findByPhoneNumber(String phoneNumber);

    /**
     * 根据账号密码查询管理员
     *
     * @param userName
     * @param loginPwd
     * @return
     */
    AdminEntity findByUserNameAndLoginPwd(String userName, String loginPwd);

    /**
     * 查询当前管理员的所有主子账号
     *
     * @param id
     * @return
     */
    R<List<AdminVO>> queryChildAccountList(Long id);

    /**
     * 查询管理员账号详情
     *
     * @param accountId
     * @return
     */
    R<AdminInfoVO> queryAccountDetail(Long id, Long accountId);

    /**
     * 创建或修改角色及角色拥有的权限
     *
     * @param roleMenusDTO
     * @param id
     * @return
     */
    R createOrUpdateRoleMenus(RoleMenusDTO roleMenusDTO, Long id);

    /**
     * 查询当前管理人员所创建的角色
     *
     * @param id
     * @return
     */
    R<List<RolesVO>> queryRole(Long id);


    /**
     * 创建或修改子账号及子账号的角色分配
     *
     * @param childAccountDTO
     * @param id
     * @return
     */
    R createOrUpdateChildAccount(ChildAccountDTO childAccountDTO, Long id);

    /**
     * 停用、删除、启用子账号
     * @param childAccountId
     * @param childAccountType
     * @return
     */
    R operateChildAccount(Long childAccountId, ChildAccountType childAccountType, Long id);

    /**
     * 查询当前管理人员的角色列表
     * @param id
     * @return
     */
    R<List<RoleMenusVO>> queryRoleList(Long id);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    R removeRole(Long roleId);

    /**
     * 查询角色的详情
     * @param roleId
     * @return
     */
    R<RoleMenuInfoVO> queryRoleInfo(Long roleId);
}

