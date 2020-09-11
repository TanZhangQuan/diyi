package com.lgyun.system.user.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.AgentMainServiceProviderEntity;
import com.lgyun.system.user.mapper.AgentMainServiceProviderMapper;
import com.lgyun.system.user.service.IAgentMainServiceProviderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 渠道商-服务商关联表 Service 实现
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
@Slf4j
@Service
@AllArgsConstructor
public class AgentMainServiceProviderServiceImpl extends BaseServiceImpl<AgentMainServiceProviderMapper, AgentMainServiceProviderEntity> implements IAgentMainServiceProviderService {

}
