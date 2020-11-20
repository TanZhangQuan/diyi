package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.AgentMainWorkerEntity;
import com.lgyun.system.user.mapper.AgentMainWorkerMapper;
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
