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
import com.lgyun.system.user.entity.AgentMainEntity;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
import com.lgyun.system.user.mapper.AgentMainWorkerMapper;
import com.lgyun.system.user.service.IAgentMainService;
import com.lgyun.system.user.service.IAgentMainWorkerService;
import com.lgyun.system.user.vo.AgentMainWorkerDetailVO;
import com.lgyun.system.user.vo.AgentMainWorkerInfoVO;
import com.lgyun.system.user.vo.AgentMainWorkerVO;
import com.lgyun.system.vo.RoleMenuInfoVO;
import com.lgyun.system.vo.RoleMenusVO;
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
 * 渠道商员工表 Service 实现
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgentMainWorkerServiceImpl extends BaseServiceImpl<AgentMainWorkerMapper, AgentMainWorkerEntity> implements IAgentMainWorkerService {

    private final ISysClient sysClient;

    @Autowired
    @Lazy
    private IAgentMainService agentMainService;

    @Override
    public R<AgentMainWorkerEntity> currentAgentMainWorker(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("用户未登录");
        }

        if (!(UserType.AGENTMAIN.equals(bladeUser.getUserType()))) {
            return R.fail("用户类型有误");
        }

        AgentMainWorkerEntity agentMainWorkerEntity = getById(bladeUser.getUserId());
        if (agentMainWorkerEntity == null) {
            return R.fail("渠道商员工不存在");
        }

        if (!(AccountState.NORMAL.equals(agentMainWorkerEntity.getAgentMainWorkerState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        AgentMainEntity agentMainEntity = agentMainService.getById(agentMainWorkerEntity.getAgentMainId());
        if (agentMainEntity == null) {
            return R.fail("渠道商不存在");
        }

        if (!(AccountState.NORMAL.equals(agentMainEntity.getAgentMainState()))) {
            return R.fail("渠道商状态非正常，请联系客服");
        }

        return R.data(agentMainWorkerEntity);
    }

    @Override
    public R<AgentMainWorkerDetailVO> queryAgentMainWorkerDetail(Long agentMainWorkerId) {
        return R.data(baseMapper.queryAgentMainWorkerDetail(agentMainWorkerId));
    }

    @Override
    public int findCountByPhoneNumber(String phoneNumber) {
        QueryWrapper<AgentMainWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentMainWorkerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public AgentMainWorkerEntity findByEmployeeUserNameAndEmployeePwd(String employeeUserName, String employeePwd) {
        QueryWrapper<AgentMainWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentMainWorkerEntity::getEmployeeUserName, employeeUserName)
                .eq(AgentMainWorkerEntity::getEmployeePwd, employeePwd);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public AgentMainWorkerEntity findByPhoneNumber(String phoneNumber) {
        QueryWrapper<AgentMainWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentMainWorkerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createOrUpdateRoleMenus(AgentMainWorkerEntity agentMainWorkerEntity, RoleMenusDTO roleMenusDTO) {
        if (!agentMainWorkerEntity.getSuperAdmin()) {
            if (!sysClient.queryMenusByRole(agentMainWorkerEntity.getRoleId()).containsAll(Arrays.asList(roleMenusDTO.getMenus()))) {
                return R.fail("只能分配您拥有的菜单");
            }
        }
        if (roleMenusDTO.getRoleId() != null && roleMenusDTO.getRoleId() != 0) {
            int count = this.count(new QueryWrapper<AgentMainWorkerEntity>().lambda().eq(AgentMainWorkerEntity::getRoleId, roleMenusDTO.getRoleId()));
            if (count > 0) {
                R.fail("您编辑的角色现在赋予给了子账号，请收回后在编辑！");
            }
        }
        roleMenusDTO.setUserType(UserType.AGENTMAIN);
        R result = sysClient.createOrUpdateRoleMenus(roleMenusDTO, agentMainWorkerEntity.getId());
        if (result.isSuccess()) {
            return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
        }
        return R.success(BladeConstant.DEFAULT_FAILURE_MESSAGE);
    }

    @Override
    public R<List<RoleMenusVO>> queryRoleList(Long id) {
        return sysClient.getRoleMenusList(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> removeRole(Long roleId) {
        int count = this.count(new QueryWrapper<AgentMainWorkerEntity>().lambda().eq(AgentMainWorkerEntity::getRoleId, roleId));
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
        BeanUtils.copyProperties(role, roleMenuInfoVo);
        roleMenuInfoVo.setMenuIds(menuIds);
        roleMenuInfoVo.setRoleId(role.getId());
        return R.data(roleMenuInfoVo);
    }

    @Override
    public R<List<RolesVO>> queryRole(Long id) {
        return R.data(sysClient.getRoles(id, UserType.AGENTMAIN));
    }

    @Override
    public R<List<AgentMainWorkerVO>> queryChildAccountList(Long id) {
        List<AgentMainWorkerEntity> list = this.list(new QueryWrapper<AgentMainWorkerEntity>().lambda().eq(AgentMainWorkerEntity::getId, id).or().eq(AgentMainWorkerEntity::getUpLevelId, id));
        List<AgentMainWorkerVO> agentMainWorkerVOS = new ArrayList<>();
        list.forEach(agentMainWorkerEntity -> {
            AgentMainWorkerVO agentMainWorkerVO = new AgentMainWorkerVO();
            BeanUtil.copyProperties(agentMainWorkerEntity, agentMainWorkerVO);
            List<String> menuNames;
            if (agentMainWorkerEntity.getSuperAdmin()) {
                menuNames = sysClient.getMenuNamesAll(MenuType.AGENTMAIN);
            } else {
                menuNames = sysClient.getMenuNames(agentMainWorkerEntity.getRoleId());
            }
            agentMainWorkerVO.setMenuNames(menuNames);
            agentMainWorkerVO.setPositionName(agentMainWorkerEntity.getPositionName());
            agentMainWorkerVO.setAccountState(agentMainWorkerEntity.getAgentMainWorkerState());
            agentMainWorkerVO.setAgentMainName(agentMainWorkerEntity.getWorkerName());
            if (id.equals(agentMainWorkerEntity.getId())) {
                agentMainWorkerVO.setMaster(true);
            }
            agentMainWorkerVOS.add(agentMainWorkerVO);
        });
        return R.data(agentMainWorkerVOS);
    }

    @Override
    public R<AgentMainWorkerInfoVO> queryAccountDetail(Long id, Long accountId) {
        List<AgentMainWorkerEntity> list = this.list(new QueryWrapper<AgentMainWorkerEntity>().lambda().eq(AgentMainWorkerEntity::getUpLevelId, id));
        List<Long> subIds = new ArrayList<>();
        list.forEach(agentMainWorkerEntity -> {
            subIds.add(agentMainWorkerEntity.getId());
        });
        subIds.add(id);

        if (!subIds.contains(accountId)) {
            return R.fail("您只能查看您的信息及您下属的信息！");
        }

        AgentMainWorkerEntity agentMainWorkerEntity = getById(accountId);
        if (agentMainWorkerEntity == null) {
            R.fail("您查看的用户不存在！");
        }

        AgentMainWorkerInfoVO agentMainWorkerInfoVO = new AgentMainWorkerInfoVO();
        BeanUtil.copyProperties(agentMainWorkerEntity, agentMainWorkerInfoVO);
        agentMainWorkerInfoVO.setPositionName(agentMainWorkerEntity.getPositionName());
        agentMainWorkerInfoVO.setAgentMainName(agentMainWorkerEntity.getWorkerName());
        agentMainWorkerInfoVO.setAdminPower(agentMainWorkerEntity.getAdminPower());
        return R.data(agentMainWorkerInfoVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createOrUpdateChildAccount(ChildAccountDTO childAccountDTO, AgentMainWorkerEntity agentMainWorkerEntity) {

        if (!agentMainWorkerEntity.getAdminPower()) {
            return R.fail("您没有权限创建子账号！");
        }

        if (childAccountDTO.getChildAccountId() != null) {
            if (agentMainWorkerEntity.getId().equals(childAccountDTO.getChildAccountId())) {
                return R.fail("您不能编辑您自己！");
            }

            AgentMainWorkerEntity workerEntity = getById(childAccountDTO.getChildAccountId());
            if (workerEntity == null) {
                return R.fail("您编辑的用户不存在！");
            }
            /**
             * 修改用户名时判断是否唯一
             */
            if (!childAccountDTO.getUserName().equals(workerEntity.getEmployeeUserName())) {
                int userNameCount = this.count(new QueryWrapper<AgentMainWorkerEntity>().lambda().eq(AgentMainWorkerEntity::getEmployeeUserName, childAccountDTO.getUserName()));
                if (userNameCount > 0) {
                    return R.fail("用户名已存在！");
                }
            }
            /**
             * 修改手机号码时判断是否唯一
             */
            if (!childAccountDTO.getPhoneNumber().equals(workerEntity.getPhoneNumber())) {
                int phoneNumberCount = this.count(new QueryWrapper<AgentMainWorkerEntity>().lambda().eq(AgentMainWorkerEntity::getPhoneNumber, childAccountDTO.getPhoneNumber()));
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
            int userNameCount = this.count(new QueryWrapper<AgentMainWorkerEntity>().lambda().eq(AgentMainWorkerEntity::getEmployeeUserName, childAccountDTO.getUserName()));
            if (userNameCount > 0) {
                return R.fail("用户名已存在！");
            }
            int phoneNumberCount = this.count(new QueryWrapper<AgentMainWorkerEntity>().lambda().eq(AgentMainWorkerEntity::getPhoneNumber, childAccountDTO.getPhoneNumber()));
            if (phoneNumberCount > 0) {
                return R.fail("手机号码已存在！");
            }

            AgentMainWorkerEntity childAccount = new AgentMainWorkerEntity();
            childAccount.setWorkerName(childAccountDTO.getName());
            childAccount.setPositionName(childAccountDTO.getPositionName());
            childAccount.setPhoneNumber(childAccountDTO.getPhoneNumber());
            childAccount.setEmployeeUserName(childAccountDTO.getUserName());
            childAccount.setRoleId(childAccountDTO.getRoleId());
            childAccount.setAgentMainId(agentMainWorkerEntity.getAgentMainId());
            childAccount.setAdminPower(childAccountDTO.getAdminPower());
            childAccount.setUpLevelId(agentMainWorkerEntity.getId());
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
        List<AgentMainWorkerEntity> list = this.list(new QueryWrapper<AgentMainWorkerEntity>().lambda().eq(AgentMainWorkerEntity::getUpLevelId, id));
        List<Long> subIds = new ArrayList<>();
        list.forEach(agentMainWorkerEntity -> {
            subIds.add(agentMainWorkerEntity.getId());
        });

        if (!subIds.contains(childAccountId)) {
            return R.fail("您只能操作您创建的子账号！");
        }

        AgentMainWorkerEntity workerEntity = getById(childAccountId);
        if (workerEntity == null) {
            return R.fail("您操作的用户不存在！");
        }
        switch (childAccountType) {
            case DELETE:
                removeRole(workerEntity.getRoleId());
                break;
            case STARTUSING:
                workerEntity.setAgentMainWorkerState(AccountState.NORMAL);
                updateById(workerEntity);
                break;
            case BLOCKUP:
                workerEntity.setAgentMainWorkerState(AccountState.FREEZE);
                updateById(workerEntity);
                break;
            default:
                break;
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

}
