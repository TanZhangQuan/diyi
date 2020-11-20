package com.lgyun.system.user.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.AgentPersonEntity;
import com.lgyun.system.user.mapper.AgentPersonMapper;
import com.lgyun.system.user.service.IAgentPersonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 渠道商人员表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
@Slf4j
@Service
@AllArgsConstructor
public class AgentPersonServiceImpl extends BaseServiceImpl<AgentPersonMapper, AgentPersonEntity> implements IAgentPersonService {

}
