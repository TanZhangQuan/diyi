package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.AgentMainServiceProviderMapper;
import com.lgyun.system.user.service.IAgentMainService;
import com.lgyun.system.user.service.IAgentMainServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.vo.CooperationServiceProviderListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private IAgentMainService agentMainService;
    private IServiceProviderService serviceProviderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> relevanceAgentMainServiceProvider(Long agentMainId, Long serviceProviderId, String matchDesc) {

        AgentMainEntity agentMainEntity = agentMainService.getById(agentMainId);
        if (agentMainEntity == null) {
            return R.fail("渠道商不存在");
        }

        ServiceProviderEntity serviceProviderEntity = serviceProviderService.getById(serviceProviderId);
        if (serviceProviderEntity == null) {
            return R.fail("服务商不存在");
        }

        AgentMainServiceProviderEntity agentMainServiceProviderEntity = queryByAgentMainAndServiceProvider(agentMainId, serviceProviderId);
        if (agentMainServiceProviderEntity == null) {
            agentMainServiceProviderEntity = new AgentMainServiceProviderEntity();
            agentMainServiceProviderEntity.setAgentMainId(agentMainId);
            agentMainServiceProviderEntity.setServiceProviderId(serviceProviderId);
            agentMainServiceProviderEntity.setMatchDesc(matchDesc);
            save(agentMainServiceProviderEntity);
        } else {
            if (!(CooperateStatus.COOPERATING.equals(agentMainServiceProviderEntity.getCooperateStatus()))) {
                agentMainServiceProviderEntity.setCooperateStatus(CooperateStatus.COOPERATING);
                updateById(agentMainServiceProviderEntity);
            }
        }

        return R.success("匹配服务商成功");
    }

    @Override
    public AgentMainServiceProviderEntity queryByAgentMainAndServiceProvider(Long agentMainId, Long serviceProviderId) {
        QueryWrapper<AgentMainServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentMainServiceProviderEntity::getAgentMainId, agentMainId).
                eq(AgentMainServiceProviderEntity::getServiceProviderId, serviceProviderId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<IPage<CooperationServiceProviderListVO>> queryCooperationServiceProviderList(Long agentMainId, String serviceProviderName, IPage<CooperationServiceProviderListVO> page) {
        return R.data(page.setRecords(baseMapper.queryCooperationServiceProviderList(agentMainId, serviceProviderName, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateCooperationStatus(Long agentMainId, Long serviceProviderId, CooperateStatus cooperateStatus) {

        int agentMainNum = agentMainService.queryCountById(agentMainId);
        if (agentMainNum <= 0) {
            return R.fail("渠道商不存在");
        }

        int serviceProviderNum = serviceProviderService.queryCountById(serviceProviderId);
        if (serviceProviderNum <= 0) {
            return R.fail("服务商不存在");
        }

        AgentMainServiceProviderEntity agentMainServiceProviderEntity = queryByAgentMainAndServiceProvider(agentMainId, serviceProviderId);
        if (agentMainServiceProviderEntity == null) {
            return R.fail("商户服务商不存在合作关系");
        }

        if (!(agentMainServiceProviderEntity.getCooperateStatus().equals(cooperateStatus))) {
            agentMainServiceProviderEntity.setCooperateStatus(cooperateStatus);
            updateById(agentMainServiceProviderEntity);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

}
