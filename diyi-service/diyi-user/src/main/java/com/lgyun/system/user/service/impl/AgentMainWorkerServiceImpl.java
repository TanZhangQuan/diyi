package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.AgentMainEntity;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
import com.lgyun.system.user.mapper.AgentMainWorkerMapper;
import com.lgyun.system.user.service.IAgentMainService;
import com.lgyun.system.user.service.IAgentMainWorkerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 渠道商员工表 Service 实现
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
@Slf4j
@Service
@AllArgsConstructor
public class AgentMainWorkerServiceImpl extends BaseServiceImpl<AgentMainWorkerMapper, AgentMainWorkerEntity> implements IAgentMainWorkerService {

    private IAgentMainService agentMainService;

    @Override
    public R<AgentMainWorkerEntity> currentAgentMainWorker(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("用户未登录");
        }

        AgentMainWorkerEntity agentMainWorkerEntity = findByUserId(bladeUser.getUserId());
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
    public AgentMainWorkerEntity findByUserId(Long userId) {
        QueryWrapper<AgentMainWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentMainWorkerEntity::getUserId, userId);
        return baseMapper.selectOne(queryWrapper);
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

}
