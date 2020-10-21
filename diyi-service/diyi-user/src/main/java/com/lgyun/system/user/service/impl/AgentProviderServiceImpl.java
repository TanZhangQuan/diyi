package com.lgyun.system.user.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.user.mapper.AgentProviderMapper;
import com.lgyun.system.user.entity.AgentProviderEntity;
import com.lgyun.system.user.service.IAgentProviderService;

/**
 * 渠道商人员表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-10-20 18:25:03
 */
@Slf4j
@Service
@AllArgsConstructor
public class AgentProviderServiceImpl extends BaseServiceImpl<AgentProviderMapper, AgentProviderEntity> implements IAgentProviderService {

}
