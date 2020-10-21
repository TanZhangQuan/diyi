package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.AgentProviderEntity;
import com.lgyun.system.user.mapper.AgentProviderMapper;
import com.lgyun.system.user.service.IAgentProviderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 渠道商服务商表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-10-20 18:25:03
 */
@Slf4j
@Service
@AllArgsConstructor
public class AgentProviderServiceImpl extends BaseServiceImpl<AgentProviderMapper, AgentProviderEntity> implements IAgentProviderService {

    @Override
    public List<Long> getServiceProviderIdByAgentMainId(Long agentMainId) {
        QueryWrapper<AgentProviderEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("agent_id", agentMainId).eq("is_deleted", 0);
        List<AgentProviderEntity> list = baseMapper.selectList(queryWrapper);
        List<Long> longList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            longList.add(list.get(i).getServiceProviderId());
        }
        return longList;
    }
}
