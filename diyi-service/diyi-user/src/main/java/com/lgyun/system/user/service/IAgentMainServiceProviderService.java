package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.AgentMainServiceProviderEntity;
import com.lgyun.system.user.vo.CooperationServiceProviderListVO;

/**
 * 渠道商-服务商关联表 Service 接口
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
public interface IAgentMainServiceProviderService extends BaseService<AgentMainServiceProviderEntity> {

    /**
     * 创建渠道商服务商关联
     *
     * @param agentMainId
     * @param serviceProviderId
     * @param matchDesc
     * @param adminEntity
     * @return
     */
    R<String> relevanceAgentMainServiceProvider(Long agentMainId, Long serviceProviderId, String matchDesc, AdminEntity adminEntity);

    /**
     * 根据渠道商, 服务商查询关联
     *
     * @param agentMainId
     * @param serviceProviderId
     * @return
     */
    AgentMainServiceProviderEntity queryByAgentMainAndServiceProvider(Long agentMainId, Long serviceProviderId);

    /**
     * 查询渠道商合作服务商
     *
     * @param agentMainId
     * @param serviceProviderName
     * @param page
     * @return
     */
    R<IPage<CooperationServiceProviderListVO>> queryCooperationServiceProviderList(Long agentMainId, String serviceProviderName, IPage<CooperationServiceProviderListVO> page);

    /**
     * 更改渠道商服务商合作关系
     *
     * @param agentMainId
     * @param serviceProviderId
     * @param cooperateStatus
     * @return
     */
    R<String> updateCooperationStatus(Long agentMainId, Long serviceProviderId, CooperateStatus cooperateStatus);
}

