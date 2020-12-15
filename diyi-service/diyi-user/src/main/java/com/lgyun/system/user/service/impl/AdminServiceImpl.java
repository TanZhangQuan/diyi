package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.ChildAccountType;
import com.lgyun.common.enumeration.MenuType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.exception.CustomException;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.dto.RoleMenusDTO;
import com.lgyun.system.entity.Role;
import com.lgyun.system.feign.ISysClient;
import com.lgyun.system.user.dto.ChildAccountDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.mapper.AdminMapper;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.vo.AdminDetailVO;
import com.lgyun.system.user.vo.AdminInfoVO;
import com.lgyun.system.user.vo.AdminVO;
import com.lgyun.system.vo.RoleMenuInfoVO;
import com.lgyun.system.vo.RoleMenusVO;
import com.lgyun.system.vo.RolesVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 平台管理员信息表 Service 实现
 *
 * @author tzq
 * @since 2020-09-19 15:02:12
 */
@Slf4j
@Service
@AllArgsConstructor
public class AdminServiceImpl extends BaseServiceImpl<AdminMapper, AdminEntity> implements IAdminService {

    private final ISysClient sysClient;

    @Override
    public R<AdminEntity> currentAdmin(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("用户未登录");
        }

        if (!(UserType.ADMIN.equals(bladeUser.getUserType()))) {
            return R.fail("用户类型有误");
        }

        AdminEntity adminEntity = getById(bladeUser.getUserId());
        if (adminEntity == null) {
            return R.fail("管理员不存在");
        }

        if (!(AccountState.NORMAL.equals(adminEntity.getAdminState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        return R.data(adminEntity);
    }

    @Override
    public R<AdminDetailVO> queryAdminDetail(Long adminId) {
        return R.data(baseMapper.queryAdminDetail(adminId));
    }

    @Override
    public int findCountByPhoneNumber(String phoneNumber) {
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public AdminEntity findByPhoneNumber(String phoneNumber) {
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public AdminEntity findByUserNameAndLoginPwd(String userName, String loginPwd) {
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminEntity::getUserName, userName).or().eq(AdminEntity::getPhoneNumber, userName).eq(AdminEntity::getLoginPwd, loginPwd);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<List<AdminVO>> queryChildAccountList(Long id) {
        List<AdminEntity> list = this.list(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getId, id).or().eq(AdminEntity::getUpLevelId, id));
        List<AdminVO> adminVOS = new ArrayList<>();
        list.forEach(adminEntity -> {
            AdminVO adminVO = new AdminVO();
            BeanUtil.copyProperties(adminEntity, adminVO);
            List<String> menuNames = null;
            if (adminEntity.getSuperAdmin()) {
                menuNames = sysClient.getMenuNamesAll(MenuType.ADMIN);
            } else {
                menuNames = sysClient.getMenuNames(adminEntity.getRoleId());
            }
            adminVO.setMenuNames(menuNames);
            adminVO.setPositionName(adminEntity.getPositionName());
            adminVO.setAdminState(adminEntity.getAdminState());
            if (id.equals(adminEntity.getId())) {
                adminVO.setMaster(true);
            }
            adminVOS.add(adminVO);
        });
        return R.data(adminVOS);
    }

    @Override
    public R<AdminInfoVO> queryAccountDetail(Long id, Long accountId) {
        List<AdminEntity> list = this.list(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getUpLevelId, id));
        List<Long> subIds = new ArrayList<>();
        list.forEach(adminEntity -> {
            subIds.add(adminEntity.getId());
        });
        subIds.add(id);
        if (!subIds.contains(accountId)) {
            return R.fail("您只能查看您的信息及您下属的信息！");
        }
        AdminEntity subAdminEntity = getById(accountId);
        if (subAdminEntity == null) {
            R.fail("您查看的用户不存在！");
        }
        AdminInfoVO adminInfoVO = new AdminInfoVO();
        BeanUtil.copyProperties(subAdminEntity, adminInfoVO);
        adminInfoVO.setPositionName(subAdminEntity.getPositionName());
        return R.data(adminInfoVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createOrUpdateRoleMenus(RoleMenusDTO roleMenusDTO, AdminEntity adminEntity) {
        if (!adminEntity.getSuperAdmin()) {
            if (!sysClient.queryMenusByRole(adminEntity.getRoleId()).containsAll(Arrays.asList(StringUtils.split(roleMenusDTO.getMenus(), ",")))) {
                return R.fail("只能分配您拥有的菜单");
            }
        }
        if (roleMenusDTO.getRoleId() != null && roleMenusDTO.getRoleId() != 0) {
            int count = this.count(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getRoleId, roleMenusDTO.getRoleId()));
            if (count > 0) {
                R.fail("您编辑的角色现在赋予给了子账号，请收回后再编辑！");
            }
        }
        roleMenusDTO.setUserType(UserType.ADMIN);
        R result = sysClient.createOrUpdateRoleMenus(roleMenusDTO, adminEntity.getId());
        if (result.isSuccess()) {
            return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
        }
        return R.success(BladeConstant.DEFAULT_FAILURE_MESSAGE);
    }

    @Override
    public R<List<RolesVO>> queryRole(Long id) {
        return R.data(sysClient.getRoles(id, UserType.ADMIN));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createOrUpdateChildAccount(ChildAccountDTO childAccountDTO, AdminEntity adminEntity) {

        if (!(adminEntity.getAdminPower())) {
            return R.fail("您没有权限创建子账号！");
        }

        if (childAccountDTO.getChildAccountId() != null && childAccountDTO.getChildAccountId() != 0) {
            if (childAccountDTO.getChildAccountId().equals(adminEntity.getId())) {
                return R.fail("您不能编辑您自己！");
            }

            AdminEntity editAdminEntity = getById(childAccountDTO.getChildAccountId());
            if (editAdminEntity == null) {
                return R.fail("您编辑的用户不存在！");
            }

            /**
             * 修改用户名时判断是否唯一
             */
            if (!childAccountDTO.getUserName().equals(editAdminEntity.getUserName())) {
                int userNameCount = this.count(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getUserName, childAccountDTO.getUserName()));
                if (userNameCount > 0) {
                    return R.fail("用户名已存在！");
                }
            }
            /**
             * 修改手机号码时判断是否唯一
             */
            if (!childAccountDTO.getPhoneNumber().equals(editAdminEntity.getPhoneNumber())) {
                int phoneNumberCount = this.count(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getPhoneNumber, childAccountDTO.getPhoneNumber()));
                if (phoneNumberCount > 0) {
                    return R.fail("手机号码已存在！");
                }
            }
            BeanUtils.copyProperties(childAccountDTO, editAdminEntity);
            editAdminEntity.setSuperAdmin(false);
            if (!StringUtils.isBlank(childAccountDTO.getPassWord())) {
                String encrypt = DigestUtil.encrypt(childAccountDTO.getPassWord());
                editAdminEntity.setLoginPwd(encrypt);
            }
            /**
             * 修改管理员表
             */
            updateById(editAdminEntity);

        } else {
            int userNameCount = this.count(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getUserName, childAccountDTO.getUserName()));
            if (userNameCount > 0) {
                return R.fail("用户名已存在！");
            }
            int phoneNumberCount = this.count(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getPhoneNumber, childAccountDTO.getPhoneNumber()));
            if (phoneNumberCount > 0) {
                return R.fail("手机号码已存在！");
            }

            AdminEntity childAccount = new AdminEntity();
            BeanUtils.copyProperties(childAccountDTO, childAccount);
            childAccount.setUpLevelId(adminEntity.getId());
            childAccount.setSuperAdmin(false);
            childAccount.setAdminState(AccountState.NORMAL);
            if (StringUtils.isBlank(childAccountDTO.getPassWord())) {
                throw new CustomException("请输入初始密码");
            }
            if (!(childAccountDTO.getPassWord().length() >= 6 && childAccountDTO.getPassWord().length() <= 18)) {
                throw new CustomException("请输入6-18位的密码！");
            }
            String encrypt = DigestUtil.encrypt(childAccountDTO.getPassWord());
            childAccount.setLoginPwd(encrypt);
            save(childAccount);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R operateChildAccount(Long childAccountId, ChildAccountType childAccountType, Long id) {
        if (id == childAccountId) {
            return R.fail("您不能删除、停用、启用您自己的账号！");
        }
        List<AdminEntity> list = this.list(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getUpLevelId, id));
        List<Long> subIds = new ArrayList<>();
        list.forEach(adminEntity -> {
            subIds.add(adminEntity.getId());
        });

        if (!subIds.contains(childAccountId)) {
            return R.fail("您只能操作您创建的子账号！");
        }

        AdminEntity adminEntity = getById(childAccountId);
        if (adminEntity == null) {
            return R.fail("您操作的用户不存在！");
        }
        switch (childAccountType) {
            case DELETE:
                removeRole(adminEntity.getRoleId());
                break;
            case STARTUSING:
                adminEntity.setAdminState(AccountState.NORMAL);
                updateById(adminEntity);
                break;
            case BLOCKUP:
                adminEntity.setAdminState(AccountState.FREEZE);
                updateById(adminEntity);
                break;
            default:
                break;
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<List<RoleMenusVO>> queryRoleList(Long id) {
        return sysClient.getRoleMenusList(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R removeRole(Long roleId) {
        int count = this.count(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getRoleId, roleId));
        if (count > 0) {
            return R.fail("您删除的角色现在赋予给了子账号，请收回后在删除！");
        }
        return sysClient.removeRole(roleId);
    }

    @Override
    public R<RoleMenuInfoVO> queryRoleInfo(Long roleId) {
        Role role = sysClient.getRole(roleId);
        if (role == null) {
            return R.fail("您输入的角色ID不存在！");
        }
        List<String> menuIds = sysClient.queryMenusByRole(roleId);
        RoleMenuInfoVO roleMenuInfoVo = new RoleMenuInfoVO();
        BeanUtils.copyProperties(role,roleMenuInfoVo);
        roleMenuInfoVo.setMenuIds(menuIds);
        roleMenuInfoVo.setRoleId(role.getId());
        return R.data(roleMenuInfoVo);
    }
}
