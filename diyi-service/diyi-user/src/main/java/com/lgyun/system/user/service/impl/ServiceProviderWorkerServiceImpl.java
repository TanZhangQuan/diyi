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
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.mapper.ServiceProviderWorkerMapper;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import com.lgyun.system.user.vo.ServiceProviderWorkerDetailVO;
import com.lgyun.system.user.vo.ServiceProviderWorkerInfoVO;
import com.lgyun.system.user.vo.ServiceProviderWorkerVO;
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
 * 服务商员工表 Service 实现
 *
 * @author tzq
 * @since 2020-08-13 17:05:17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceProviderWorkerServiceImpl extends BaseServiceImpl<ServiceProviderWorkerMapper, ServiceProviderWorkerEntity> implements IServiceProviderWorkerService {

    private final ISysClient sysClient;

    @Autowired
    @Lazy
    private IServiceProviderService serviceProviderService;

    @Override
    public R<ServiceProviderWorkerEntity> currentServiceProviderWorker(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("用户未登录");
        }

        if (!(UserType.SERVICEPROVIDER.equals(bladeUser.getUserType()))) {
            return R.fail("用户类型有误");
        }

        ServiceProviderWorkerEntity serviceProviderWorkerEntity = getById(bladeUser.getUserId());
        if (serviceProviderWorkerEntity == null) {
            return R.fail("服务商员工不存在");
        }

        if (!(AccountState.NORMAL.equals(serviceProviderWorkerEntity.getServiceProviderWorkerState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        ServiceProviderEntity serviceProviderEntity = serviceProviderService.getById(serviceProviderWorkerEntity.getServiceProviderId());
        if (serviceProviderEntity == null) {
            return R.fail("服务商不存在");
        }

        if (!(AccountState.NORMAL.equals(serviceProviderEntity.getServiceProviderState()))) {
            return R.fail("服务商状态非正常，请联系客服");
        }

        return R.data(serviceProviderWorkerEntity);
    }

    @Override
    public R<ServiceProviderWorkerDetailVO> queryServiceProviderWorkerDetail(Long serviceProviderWorkerId) {
        return R.data(baseMapper.queryServiceProviderWorkerDetail(serviceProviderWorkerId));
    }

    @Override
    public ServiceProviderWorkerEntity findByPhoneNumber(String phoneNumber) {
        QueryWrapper<ServiceProviderWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderWorkerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public ServiceProviderWorkerEntity findByAccountAndPwd(String account, String employeePwd) {
        QueryWrapper<ServiceProviderWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderWorkerEntity::getEmployeeUserName, account).or().eq(ServiceProviderWorkerEntity::getPhoneNumber, account)
                .eq(ServiceProviderWorkerEntity::getEmployeePwd, employeePwd);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int findCountByPhoneNumber(String phoneNumber) {
        QueryWrapper<ServiceProviderWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderWorkerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createOrUpdateRoleMenus(ServiceProviderWorkerEntity serviceProviderWorkerEntity, RoleMenusDTO roleMenusDTO) {
        if (!serviceProviderWorkerEntity.getSuperAdmin()) {
            if (!sysClient.queryMenusByRole(serviceProviderWorkerEntity.getRoleId()).containsAll(Arrays.asList(roleMenusDTO.getMenus()))) {
                return R.fail("只能分配您拥有的菜单");
            }
        }
        if (roleMenusDTO.getRoleId() != null && roleMenusDTO.getRoleId() != 0) {
            int count = this.count(new QueryWrapper<ServiceProviderWorkerEntity>().lambda().eq(ServiceProviderWorkerEntity::getRoleId, roleMenusDTO.getRoleId()));
            if (count > 0) {
                R.fail("您编辑的角色现在赋予给了子账号，请收回后在编辑！");
            }
        }
        roleMenusDTO.setUserType(UserType.SERVICEPROVIDER);
        R result = sysClient.createOrUpdateRoleMenus(roleMenusDTO, serviceProviderWorkerEntity.getId());
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
        int count = this.count(new QueryWrapper<ServiceProviderWorkerEntity>().lambda().eq(ServiceProviderWorkerEntity::getRoleId, roleId));
        if (count > 0) {
            return R.fail("您删除的角色现在赋予给了子账号，请收回后在删除！");
        }
        return sysClient.removeRole(roleId);
    }

    @Override
    public R<List<RolesVO>> queryRole(Long id) {
        return R.data(sysClient.getRoles(id, UserType.SERVICEPROVIDER));
    }

    @Override
    public R<List<ServiceProviderWorkerVO>> queryChildAccountList(Long id) {
        List<ServiceProviderWorkerEntity> list = this.list(new QueryWrapper<ServiceProviderWorkerEntity>().lambda().eq(ServiceProviderWorkerEntity::getId, id).or().eq(ServiceProviderWorkerEntity::getUpLevelId, id));
        List<ServiceProviderWorkerVO> serviceProviderWorkerVOS = new ArrayList<>();
        list.forEach(serviceProviderWorkerEntity -> {
            ServiceProviderWorkerVO serviceProviderWorkerVO = new ServiceProviderWorkerVO();
            BeanUtil.copyProperties(serviceProviderWorkerEntity, serviceProviderWorkerVO);
            List<String> menuNames;
            if (serviceProviderWorkerEntity.getSuperAdmin()) {
                menuNames = sysClient.getMenuNamesAll(MenuType.SERVICEPROVIDER);
            } else {
                menuNames = sysClient.getMenuNames(serviceProviderWorkerEntity.getRoleId());
            }
            serviceProviderWorkerVO.setMenuNames(menuNames);
            serviceProviderWorkerVO.setPositionName(serviceProviderWorkerEntity.getPositionName());
            serviceProviderWorkerVO.setAccountState(serviceProviderWorkerEntity.getServiceProviderWorkerState());
            serviceProviderWorkerVO.setEnterpriseName(serviceProviderWorkerEntity.getWorkerName());
            if (id.equals(serviceProviderWorkerEntity.getId())) {
                serviceProviderWorkerVO.setMaster(true);
            }
            serviceProviderWorkerVOS.add(serviceProviderWorkerVO);
        });

        return R.data(serviceProviderWorkerVOS);
    }

    @Override
    public R<ServiceProviderWorkerInfoVO> queryAccountDetail(Long id, Long accountId) {
        List<ServiceProviderWorkerEntity> list = this.list(new QueryWrapper<ServiceProviderWorkerEntity>().lambda().eq(ServiceProviderWorkerEntity::getUpLevelId, id));
        List<Long> subIds = new ArrayList<>();
        list.forEach(enterpriseWorkerEntity -> {
            subIds.add(enterpriseWorkerEntity.getId());
        });
        subIds.add(id);
        if (!subIds.contains(accountId)) {
            return R.fail("您只能查看您的信息及您下属的信息！");
        }
        ServiceProviderWorkerEntity subServiceProviderWorkerEntity = getById(accountId);
        if (subServiceProviderWorkerEntity == null) {
            R.fail("您查看的用户不存在！");
        }
        ServiceProviderWorkerInfoVO serviceProviderWorkerInfoVO = new ServiceProviderWorkerInfoVO();
        BeanUtil.copyProperties(subServiceProviderWorkerEntity, serviceProviderWorkerInfoVO);
        serviceProviderWorkerInfoVO.setPositionName(subServiceProviderWorkerEntity.getPositionName());
        serviceProviderWorkerInfoVO.setEnterpriseName(subServiceProviderWorkerEntity.getWorkerName());
        serviceProviderWorkerInfoVO.setAdminPower(subServiceProviderWorkerEntity.getAdminPower());
        return R.data(serviceProviderWorkerInfoVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createOrUpdateChildAccount(ChildAccountDTO childAccountDTO, ServiceProviderWorkerEntity serviceProviderWorkerEntity) {

        if (!serviceProviderWorkerEntity.getAdminPower()) {
            return R.fail("您没有权限创建子账号！");
        }

        if (childAccountDTO.getChildAccountId() != null) {
            if (serviceProviderWorkerEntity.getId().equals(childAccountDTO.getChildAccountId())) {
                return R.fail("您不能编辑您自己！");
            }
            ServiceProviderWorkerEntity workerEntity = getById(childAccountDTO.getChildAccountId());
            if (workerEntity == null) {
                return R.fail("您编辑的用户不存在！");
            }
            /**
             * 修改用户名时判断是否唯一
             */
            if (!childAccountDTO.getUserName().equals(workerEntity.getEmployeeUserName())) {
                int userNameCount = this.count(new QueryWrapper<ServiceProviderWorkerEntity>().lambda().eq(ServiceProviderWorkerEntity::getEmployeeUserName, childAccountDTO.getUserName()));
                if (userNameCount > 0) {
                    return R.fail("用户名已存在！");
                }
            }
            /**
             * 修改手机号码时判断是否唯一
             */
            if (!childAccountDTO.getPhoneNumber().equals(workerEntity.getPhoneNumber())) {
                int phoneNumberCount = this.count(new QueryWrapper<ServiceProviderWorkerEntity>().lambda().eq(ServiceProviderWorkerEntity::getPhoneNumber, childAccountDTO.getPhoneNumber()));
                if (phoneNumberCount > 0) {
                    return R.fail("手机号码已存在！");
                }
            }
            workerEntity.setWorkerName(childAccountDTO.getName());
            workerEntity.setPositionName(childAccountDTO.getPositionName());
            workerEntity.setPhoneNumber(childAccountDTO.getPhoneNumber());
            workerEntity.setEmployeeUserName(childAccountDTO.getUserName());
            workerEntity.setRoleId(childAccountDTO.getRoleId());
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
            int userNameCount = this.count(new QueryWrapper<ServiceProviderWorkerEntity>().lambda().eq(ServiceProviderWorkerEntity::getEmployeeUserName, childAccountDTO.getUserName()));
            if (userNameCount > 0) {
                return R.fail("用户名已存在！");
            }
            int phoneNumberCount = this.count(new QueryWrapper<ServiceProviderWorkerEntity>().lambda().eq(ServiceProviderWorkerEntity::getPhoneNumber, childAccountDTO.getPhoneNumber()));
            if (phoneNumberCount > 0) {
                return R.fail("手机号码已存在！");
            }

            ServiceProviderWorkerEntity childAccount = new ServiceProviderWorkerEntity();
            childAccount.setWorkerName(childAccountDTO.getName());
            childAccount.setPositionName(childAccountDTO.getPositionName());
            childAccount.setPhoneNumber(childAccountDTO.getPhoneNumber());
            childAccount.setEmployeeUserName(childAccountDTO.getUserName());
            childAccount.setRoleId(childAccountDTO.getRoleId());
            childAccount.setServiceProviderId(serviceProviderWorkerEntity.getServiceProviderId());
            childAccount.setAdminPower(childAccountDTO.getAdminPower());
            childAccount.setUpLevelId(serviceProviderWorkerEntity.getId());
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
        if (id.equals(childAccountId)) {
            return R.fail("您不能删除、停用、启用您自己的账号！");
        }
        List<ServiceProviderWorkerEntity> list = this.list(new QueryWrapper<ServiceProviderWorkerEntity>().lambda().eq(ServiceProviderWorkerEntity::getUpLevelId, id));
        List<Long> subIds = new ArrayList<>();
        list.forEach(serviceProviderWorkerEntity -> {
            subIds.add(serviceProviderWorkerEntity.getId());
        });

        if (!subIds.contains(childAccountId)) {
            return R.fail("您只能操作您创建的子账号！");
        }

        ServiceProviderWorkerEntity workerEntity = getById(childAccountId);
        if (workerEntity == null) {
            return R.fail("您操作的用户不存在！");
        }
        switch (childAccountType) {
            case DELETE:
                removeRole(workerEntity.getRoleId());
                break;
            case STARTUSING:
                workerEntity.setServiceProviderWorkerState(AccountState.NORMAL);
                updateById(workerEntity);
                break;
            case BLOCKUP:
                workerEntity.setServiceProviderWorkerState(AccountState.FREEZE);
                updateById(workerEntity);
                break;
            default:
                break;
        }
        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

}
