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
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.mapper.EnterpriseWorkerMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.vo.EnterpriseWorkerDetailVO;
import com.lgyun.system.user.vo.EnterpriseWorkerInfoVO;
import com.lgyun.system.user.vo.EnterpriseWorkerVO;
import com.lgyun.system.vo.RoleMenuInfoVO;
import com.lgyun.system.vo.RolesVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EnterpriseWorkerServiceImpl extends BaseServiceImpl<EnterpriseWorkerMapper, EnterpriseWorkerEntity> implements IEnterpriseWorkerService {

    private final ISysClient sysClient;

    @Autowired
    @Lazy
    private IEnterpriseService enterpriseService;

    @Override
    public R<EnterpriseWorkerEntity> currentEnterpriseWorker(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("用户未登录");
        }

        if (!(UserType.ENTERPRISE.equals(bladeUser.getUserType()))) {
            return R.fail("用户类型有误");
        }

        EnterpriseWorkerEntity enterpriseWorkerEntity = getById(bladeUser.getUserId());
        if (enterpriseWorkerEntity == null) {
            return R.fail("商户员工不存在");
        }

        if (!(AccountState.NORMAL.equals(enterpriseWorkerEntity.getEnterpriseWorkerState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        EnterpriseEntity enterpriseEntity = enterpriseService.getById(enterpriseWorkerEntity.getEnterpriseId());
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        if (!(AccountState.NORMAL.equals(enterpriseEntity.getEnterpriseState()))) {
            return R.fail("商户状态非正常，请联系客服");
        }

        return R.data(enterpriseWorkerEntity);
    }

    @Override
    public R<EnterpriseWorkerDetailVO> queryEnterpriseWorkerDetail(Long enterpriseWorkerId) {
        return R.data(baseMapper.queryEnterpriseWorkerDetail(enterpriseWorkerId));
    }

    @Override
    public EnterpriseWorkerEntity findByPhoneNumber(String phoneNumber) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public EnterpriseWorkerEntity findByAccountAndPwd(String account, String employeePwd) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getEmployeeUserName, account).or().eq(EnterpriseWorkerEntity::getPhoneNumber, account)
                .eq(EnterpriseWorkerEntity::getEmployeePwd, employeePwd);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int findCountByPhoneNumber(String phoneNumber) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createOrUpdateRoleMenus(EnterpriseWorkerEntity enterpriseWorkerEntity, RoleMenusDTO roleMenusDTO) {
        if (!enterpriseWorkerEntity.getSuperAdmin()) {
            if (!sysClient.queryMenusByRole(enterpriseWorkerEntity.getRoleId()).containsAll(Arrays.asList(roleMenusDTO.getMenus()))) {
                return R.fail("只能分配您拥有的菜单");
            }
        }
        if (roleMenusDTO.getRoleId() != null && roleMenusDTO.getRoleId() != 0) {
            int count = this.count(new QueryWrapper<EnterpriseWorkerEntity>().lambda().eq(EnterpriseWorkerEntity::getRoleId, roleMenusDTO.getRoleId()));
            if (count > 0) {
                R.fail("您编辑的角色现在赋予给了子账号，请收回后在编辑！");
            }
        }
        roleMenusDTO.setUserType(UserType.ENTERPRISE);
        R result = sysClient.createOrUpdateRoleMenus(roleMenusDTO, enterpriseWorkerEntity.getId());
        if (result.isSuccess()) {
            return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
        }
        return R.success(BladeConstant.DEFAULT_FAILURE_MESSAGE);
    }

    @Override
    public R queryRoleList(Long id) {
        return sysClient.getRoleMenusList(id);
    }

    @Override
    public R<RoleMenuInfoVO> queryRoleInfo(Long roleId) {
        Role role = sysClient.getRole(roleId);
        if (role == null) {
            return R.fail("您输入的角色ID不存在！");
        }
        List<String> menuIds = sysClient.queryMenusByRole(roleId);
        RoleMenuInfoVO roleMenuInfoVo = new RoleMenuInfoVO();
        BeanUtils.copyProperties(role, roleMenuInfoVo);
        roleMenuInfoVo.setMenuIds(menuIds);
        roleMenuInfoVo.setRoleId(role.getId());
        return R.data(roleMenuInfoVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> removeRole(Long roleId) {
        int count = this.count(new QueryWrapper<EnterpriseWorkerEntity>().lambda().eq(EnterpriseWorkerEntity::getRoleId, roleId));
        if (count > 0) {
            return R.fail("您删除的角色现在赋予给了子账号，请收回后在删除！");
        }
        return sysClient.removeRole(roleId);
    }

    @Override
    public R<List<RolesVO>> queryRole(Long id) {
        return R.data(sysClient.getRoles(id, UserType.ENTERPRISE));
    }

    @Override
    public R<List<EnterpriseWorkerVO>> queryChildAccountList(Long id) {
        List<EnterpriseWorkerEntity> list = this.list(new QueryWrapper<EnterpriseWorkerEntity>().lambda().eq(EnterpriseWorkerEntity::getId, id).or().eq(EnterpriseWorkerEntity::getUpLevelId, id));
        List<EnterpriseWorkerVO> enterpriseWorkerVOS = new ArrayList<>();
        list.forEach(enterpriseWorkerEntity -> {
            EnterpriseWorkerVO enterpriseWorkerVO = new EnterpriseWorkerVO();
            BeanUtil.copyProperties(enterpriseWorkerEntity, enterpriseWorkerVO);
            List<String> menuNames;
            if (enterpriseWorkerEntity.getSuperAdmin()) {
                menuNames = sysClient.getMenuNamesAll(MenuType.ENTERPRISE);
            } else {
                menuNames = sysClient.getMenuNames(enterpriseWorkerEntity.getRoleId());
            }
            enterpriseWorkerVO.setMenuName(menuNames);
            enterpriseWorkerVO.setPositionName(enterpriseWorkerEntity.getPositionName());
            enterpriseWorkerVO.setAccountState(enterpriseWorkerEntity.getEnterpriseWorkerState());
            enterpriseWorkerVO.setEnterpriseName(enterpriseWorkerEntity.getWorkerName());
            enterpriseWorkerVO.setEnterpriseWorkerId(enterpriseWorkerEntity.getId());
            if (id.equals(enterpriseWorkerEntity.getId())) {
                enterpriseWorkerVO.setMaster(true);
            }
            enterpriseWorkerVOS.add(enterpriseWorkerVO);
        });
        return R.data(enterpriseWorkerVOS);
    }

    @Override
    public R<EnterpriseWorkerInfoVO> queryAccountDetail(Long id, Long accountId) {
        List<EnterpriseWorkerEntity> list = this.list(new QueryWrapper<EnterpriseWorkerEntity>().lambda().eq(EnterpriseWorkerEntity::getUpLevelId, id));
        List<Long> subIds = new ArrayList<>();
        list.forEach(enterpriseWorkerEntity -> {
            subIds.add(enterpriseWorkerEntity.getId());
        });
        subIds.add(id);
        if (!subIds.contains(accountId)) {
            return R.fail("您只能查看您的信息及您下属的信息！");
        }
        EnterpriseWorkerEntity subEnterpriseWorkerEntity = getById(accountId);
        if (subEnterpriseWorkerEntity == null) {
            R.fail("您查看的用户不存在！");
        }
        EnterpriseWorkerInfoVO enterpriseWorkerInfoVO = new EnterpriseWorkerInfoVO();
        BeanUtil.copyProperties(subEnterpriseWorkerEntity, enterpriseWorkerInfoVO);
        enterpriseWorkerInfoVO.setPositionName(subEnterpriseWorkerEntity.getPositionName());
        enterpriseWorkerInfoVO.setEnterpriseName(subEnterpriseWorkerEntity.getWorkerName());
        enterpriseWorkerInfoVO.setEnterpriseWorkerId(subEnterpriseWorkerEntity.getId());
        return R.data(enterpriseWorkerInfoVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createOrUpdateChildAccount(ChildAccountDTO childAccountDTO, EnterpriseWorkerEntity enterpriseWorkerEntity) {

        if (!enterpriseWorkerEntity.getAdminPower()) {
            return R.fail("您没有权限创建子账号！");
        }

        if (childAccountDTO.getChildAccountId() != null && childAccountDTO.getChildAccountId() != 0) {
            if (childAccountDTO.getChildAccountId() == enterpriseWorkerEntity.getId()) {
                return R.fail("您不能编辑您自己！");
            }
            EnterpriseWorkerEntity workerEntity = getById(childAccountDTO.getChildAccountId());
            if (workerEntity == null) {
                return R.fail("您编辑的用户不存在！");
            }
            /**
             * 修改用户名时判断是否唯一
             */
            if (!childAccountDTO.getUserName().equals(workerEntity.getEmployeeUserName())) {
                int userNameCount = this.count(new QueryWrapper<EnterpriseWorkerEntity>().lambda().eq(EnterpriseWorkerEntity::getEmployeeUserName, childAccountDTO.getUserName()));
                if (userNameCount > 0) {
                    return R.fail("用户名已存在！");
                }
            }
            /**
             * 修改手机号码时判断是否唯一
             */
            if (!childAccountDTO.getPhoneNumber().equals(workerEntity.getPhoneNumber())) {
                int phoneNumberCount = this.count(new QueryWrapper<EnterpriseWorkerEntity>().lambda().eq(EnterpriseWorkerEntity::getPhoneNumber, childAccountDTO.getPhoneNumber()));
                if (phoneNumberCount > 0) {
                    return R.fail("手机号码已存在！");
                }
            }
            workerEntity.setWorkerName(childAccountDTO.getName());
            workerEntity.setPositionName(childAccountDTO.getPositionName());
            workerEntity.setPhoneNumber(childAccountDTO.getPhoneNumber());
            workerEntity.setEmployeeUserName(childAccountDTO.getUserName());
            workerEntity.setRoleId(childAccountDTO.getRoleId());
            workerEntity.setEnterpriseId(enterpriseWorkerEntity.getEnterpriseId());
            workerEntity.setAdminPower(childAccountDTO.getAdminPower());
            workerEntity.setSuperAdmin(false);
            if (!StringUtils.isBlank(childAccountDTO.getPassWord())) {
                String encrypt = DigestUtil.encrypt(childAccountDTO.getPassWord());
                workerEntity.setEmployeePwd(encrypt);
            }
            /**
             * 修改管理员表
             */
            updateById(workerEntity);

        } else {
            int userNameCount = this.count(new QueryWrapper<EnterpriseWorkerEntity>().lambda().eq(EnterpriseWorkerEntity::getEmployeeUserName, childAccountDTO.getUserName()));
            if (userNameCount > 0) {
                return R.fail("用户名已存在！");
            }
            int phoneNumberCount = this.count(new QueryWrapper<EnterpriseWorkerEntity>().lambda().eq(EnterpriseWorkerEntity::getPhoneNumber, childAccountDTO.getPhoneNumber()));
            if (phoneNumberCount > 0) {
                return R.fail("手机号码已存在！");
            }

            EnterpriseWorkerEntity childAccount = new EnterpriseWorkerEntity();
            childAccount.setWorkerName(childAccountDTO.getName());
            childAccount.setPositionName(childAccountDTO.getPositionName());
            childAccount.setPhoneNumber(childAccountDTO.getPhoneNumber());
            childAccount.setEmployeeUserName(childAccountDTO.getUserName());
            childAccount.setRoleId(childAccountDTO.getRoleId());
            childAccount.setEnterpriseId(enterpriseWorkerEntity.getEnterpriseId());
            childAccount.setAdminPower(childAccountDTO.getAdminPower());
            childAccount.setUpLevelId(enterpriseWorkerEntity.getId());
            childAccount.setSuperAdmin(false);
            if (StringUtils.isBlank(childAccountDTO.getPassWord())) {
                throw new CustomException("请输入初始密码");
            }
            if (!(childAccountDTO.getPassWord().length() >= 6 && childAccountDTO.getPassWord().length() <= 18)) {
                throw new CustomException("请输入6-18位的密码！");
            }
            String encrypt = DigestUtil.encrypt(childAccountDTO.getPassWord());
            childAccount.setEmployeePwd(encrypt);
            save(childAccount);
        }
        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> operateChildAccount(Long childAccountId, ChildAccountType childAccountType, Long id) {
        if (id == childAccountId) {
            return R.fail("您不能删除、停用、启用您自己的账号！");
        }
        List<EnterpriseWorkerEntity> list = this.list(new QueryWrapper<EnterpriseWorkerEntity>().lambda().eq(EnterpriseWorkerEntity::getUpLevelId, id));
        List<Long> subIds = new ArrayList<>();
        list.forEach(enterpriseWorkerEntity -> {
            subIds.add(enterpriseWorkerEntity.getId());
        });

        if (!subIds.contains(childAccountId)) {
            return R.fail("您只能操作您创建的子账号！");
        }

        EnterpriseWorkerEntity workerEntity = getById(childAccountId);
        if (workerEntity == null) {
            return R.fail("您操作的用户不存在！");
        }
        switch (childAccountType) {
            case DELETE:
                removeRole(workerEntity.getRoleId());
                break;
            case STARTUSING:
                workerEntity.setEnterpriseWorkerState(AccountState.NORMAL);
                updateById(workerEntity);
                break;
            case BLOCKUP:
                workerEntity.setEnterpriseWorkerState(AccountState.FREEZE);
                updateById(workerEntity);
                break;
            default:
                break;
        }
        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }
}
