package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.AgentMainEnterpriseEntity;
import com.lgyun.system.user.vo.CooperationEnterprisesListVO;

/**
 * 渠道商-商户关联表 Service 接口
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
public interface IAgentMainEnterpriseService extends BaseService<AgentMainEnterpriseEntity> {

    /**
     * 根据渠道商,商户查询关联
     *
     * @param agentMainId
     * @param enterpriseId
     * @return
     */
    AgentMainEnterpriseEntity queryByAgentMainAndEnterprise(Long agentMainId, Long enterpriseId);

    /**
     * 渠道商匹配商户
     *
     * @param agentMainId
     * @param enterpriseId
     * @param matchDesc
     * @return
     */
    R<String> relevanceAgentMainEnterprise(Long agentMainId, Long enterpriseId, String matchDesc);

    /**
     * 查询渠道商合作商户
     *
     * @param agentMainId
     * @param enterpriseName
     * @param page
     * @return
     */
    R<IPage<CooperationEnterprisesListVO>> queryCooperationEnterpriseList(Long agentMainId, String enterpriseName, IPage<CooperationEnterprisesListVO> page);

    /**
     *
     *
     * @param agentMainId
     * @param enterpriseId
     * @param cooperateStatus
     * @return
     */
    R<String> updateCooperationStatus(Long agentMainId, Long enterpriseId, CooperateStatus cooperateStatus);
}

