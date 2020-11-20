package com.lgyun.system.user.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.AgentProviderEntity;

import java.util.List;

/**
 * 渠道商服务商表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
public interface IAgentProviderService extends BaseService<AgentProviderEntity> {

    List<Long> getServiceProviderIdByAgentMainId(Long agentMainId);
}

