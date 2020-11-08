package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
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
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.mapper.AdminMapper;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IUserService;
import com.lgyun.system.user.vo.AdminDetailVO;
import com.lgyun.system.user.vo.AdminInfoVO;
import com.lgyun.system.user.vo.AdminVO;
import com.lgyun.system.vo.RoleMenuInfoVo;
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
 * @author liangfeihu
 * @since 2020-09-19 15:02:12
 */
@Slf4j
@Service
@AllArgsConstructor
public class AdminServiceImpl extends BaseServiceImpl<AdminMapper, AdminEntity> implements IAdminService {

    private final ISysClient sysClient;
    private IUserService userService;

    @Override
    public R<AdminEntity> currentAdmin(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("用户未登录");
        }

        AdminEntity adminEntity = findByUserId(bladeUser.getUserId());
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
    public AdminEntity findByUserId(Long userId) {
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminEntity::getUserId, userId);
        return baseMapper.selectOne(queryWrapper);
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
        queryWrapper.lambda().eq(AdminEntity::getUserName, userName)
                .eq(AdminEntity::getLoginPwd, loginPwd);
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
            adminVO.setPositionName(adminEntity.getPositionName().getDesc());
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
        AdminEntity subAdminEntity = this.getById(accountId);
        if (subAdminEntity == null) {
            R.fail("您查看的用户不存在！");
        }
        AdminInfoVO adminInfoVO = new AdminInfoVO();
        BeanUtil.copyProperties(subAdminEntity, adminInfoVO);
        adminInfoVO.setPositionName(subAdminEntity.getPositionName().getDesc());
        return R.data(adminInfoVO);
    }

    @Override
    public R createOrUpdateRoleMenus(RoleMenusDTO roleMenusDTO, Long id) {
        AdminEntity adminEntity = this.getById(id);
        if (!adminEntity.getSuperAdmin()) {
            if (!sysClient.getMenuIds(adminEntity.getRoleId()).contains(Arrays.asList(roleMenusDTO.getMenus()))) {
                return R.fail("只能分配您拥有的菜单！");
            }
        }
        if (roleMenusDTO.getRoleId() != null && roleMenusDTO.getRoleId() != 0) {
            int count = this.count(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getRoleId, roleMenusDTO.getRoleId()));
            if (count > 0) {
                R.fail("您编辑的角色现在赋予给了子账号，请收回后在编辑！");
            }
        }
        roleMenusDTO.setUserType(UserType.ADMIN);
        R result = sysClient.createOrUpdateRoleMenus(roleMenusDTO, id);
        if (result.isSuccess()) {
            return R.success("操作成功！");
        }
        return R.fail("操作失败！");
    }

    @Override
    public R<List<RolesVO>> queryRole(Long id) {
        return R.data(sysClient.getRoles(id, UserType.ADMIN));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createOrUpdateChildAccount(ChildAccountDTO childAccountDTO, Long id) {
        if (childAccountDTO.getChildAccountId() != null && childAccountDTO.getChildAccountId() != 0) {
            if (childAccountDTO.getChildAccountId() == id) {
                return R.fail("您不能编辑您自己！");
            }
            AdminEntity adminEntity = this.getById(childAccountDTO.getChildAccountId());
            if (adminEntity == null) {
                return R.fail("您编辑的用户不存在！");
            }
            /**
             * 修改用户名时判断是否唯一
             */
            if (!childAccountDTO.getUserName().equals(adminEntity.getUserName())) {
                int userNameCount = this.count(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getUserName, childAccountDTO.getUserName()));
                if (userNameCount > 0) {
                    return R.fail("用户名已存在！");
                }
            }
            /**
             * 修改手机号码时判断是否唯一
             */
            if (!childAccountDTO.getPhoneNumber().equals(adminEntity.getPhoneNumber())) {
                int phoneNumberCount = this.count(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getPhoneNumber, childAccountDTO.getPhoneNumber()));
                if (phoneNumberCount > 0) {
                    return R.fail("手机号码已存在！");
                }
            }
            BeanUtils.copyProperties(childAccountDTO, adminEntity);
            adminEntity.setSuperAdmin(false);
            if (!StringUtils.isBlank(childAccountDTO.getPassWord())) {
                String encrypt = DigestUtil.encrypt(childAccountDTO.getPassWord());
                adminEntity.setLoginPwd(encrypt);
            }
            /**
             * 修改管理员表
             */
            this.updateById(adminEntity);

            /**
             * 修改用户表
             */
            User user = userService.getById(adminEntity.getUserId());
            if (user == null) {
                throw new CustomException("修改的账号异常！");
            }
            user.setAccount(childAccountDTO.getUserName());
            user.setPhone(childAccountDTO.getPhoneNumber());
            user.setRoleId(childAccountDTO.getRoleId().toString());
            userService.updateById(user);
        } else {
            int userNameCount = this.count(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getUserName, childAccountDTO.getUserName()));
            if (userNameCount > 0) {
                return R.fail("用户名已存在！");
            }
            int phoneNumberCount = this.count(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getPhoneNumber, childAccountDTO.getPhoneNumber()));
            if (phoneNumberCount > 0) {
                return R.fail("手机号码已存在！");
            }
            User user = new User();
            user.setUserType(UserType.ADMIN);
            user.setRoleId(childAccountDTO.getRoleId().toString());
            user.setPhone(childAccountDTO.getPhoneNumber());
            user.setAccount(childAccountDTO.getUserName());
            userService.save(user);

            AdminEntity childAccount = new AdminEntity();
            BeanUtils.copyProperties(childAccountDTO, childAccount);
            childAccount.setUpLevelId(id);
            childAccount.setSuperAdmin(false);
            childAccount.setAdminState(AccountState.NORMAL);
            if (StringUtils.isBlank(childAccountDTO.getPassWord())) {
                throw new CustomException("初始密码不能为空！");
            }
            if (!(childAccountDTO.getPassWord().length() >= 6 && childAccountDTO.getPassWord().length() <= 18)) {
                throw new CustomException("请输入6-18位的密码！");
            }
            String encrypt = DigestUtil.encrypt(childAccountDTO.getPassWord());
            childAccount.setLoginPwd(encrypt);
            childAccount.setUserId(user.getId());
            this.save(childAccount);
        }
        return R.success("操作成功！");
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

        AdminEntity adminEntity = this.getById(childAccountId);
        if (adminEntity == null) {
            return R.fail("您操作的用户不存在！");
        }
        switch (childAccountType) {
            case DELETE:
                this.removeRole(adminEntity.getRoleId());
                userService.removeById(adminEntity.getId());
                break;
            case STARTUSING:
                adminEntity.setAdminState(AccountState.NORMAL);
                this.updateById(adminEntity);
                break;
            case BLOCKUP:
                adminEntity.setAdminState(AccountState.FREEZE);
                this.updateById(adminEntity);
                break;
            default:
                break;
        }
        return R.success("操作成功！");
    }

    @Override
    public R<List<RoleMenusVO>> queryRoleList(Long id) {
        return sysClient.getRoleMenusList(id);
    }

    @Override
    public R removeRole(Long roleId) {
        int count = this.count(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getRoleId, roleId));
        if (count > 0) {
            return R.fail("您删除的角色现在赋予给了子账号，请收回后在删除！");
        }
        return sysClient.removeRole(roleId);
    }

    @Override
    public R<RoleMenuInfoVo> queryRoleInfo(Long roleId) {
        Role role = sysClient.getRole(roleId);
        if (role == null) {
            return R.fail("您输入的角色ID不存在！");
        }
        List<String> menuIds = sysClient.getMenuIds(roleId);
        RoleMenuInfoVo roleMenuInfoVo = new RoleMenuInfoVo();
        BeanUtils.copyProperties(role,roleMenuInfoVo);
        roleMenuInfoVo.setMenuIds(menuIds);
        roleMenuInfoVo.setRoleId(role.getId());
        return R.data(roleMenuInfoVo);
    }
}
