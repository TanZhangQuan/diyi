package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.AddAdminAgentMainDTO;
import com.lgyun.system.user.dto.QueryAgentMainDTO;
import com.lgyun.system.user.dto.UpdateAgentMainDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.AgentMainEntity;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.AdminAgentMainVO;
import com.lgyun.system.user.vo.AgentMainServiceProviderVO;

/**
 * 渠道商信息表 Service 接口
 *
 * @author liangfeihu
 * @since 2020-10-20 18:25:03
 */
public interface IAgentMainService extends BaseService<AgentMainEntity> {

    /**
     * 查询商户名字是否已存在
     *
     * @param enterpriseName
     * @param enterpriseId
     * @return
     */
    Integer findCountByEnterpriseName(String enterpriseName, Long enterpriseId);

    /**
     * 查询统一社会信用代码是否已存在
     *
     * @param socialCreditNo
     * @param enterpriseId
     * @return
     */
    Integer findCountBySocialCreditNo(String socialCreditNo, Long enterpriseId);

    /**
     * 通过商户名字查询
     *
     * @param enterpriseName
     * @return
     */
    R<MakerEnterpriseRelationVO> getEnterpriseName(String enterpriseName);

    /**
     * 通过商户id查询
     *
     * @param enterpriseId
     * @param makerId
     * @return
     */
    R<MakerEnterpriseRelationVO> getEnterpriseId(Long enterpriseId, Long makerId);

    /**
     * 查询所有渠道商列表
     *
     * @param page
     * @param queryAgentMainDTO
     * @return
     */
    R<IPage<AdminAgentMainVO>> getAgentMainList(IPage<AdminAgentMainVO> page, QueryAgentMainDTO queryAgentMainDTO);

    /**
     * 非法
     *
     * @param agentMainId
     * @return
     */
    R updateIllegal(Long agentMainId, AccountState accountState, AdminEntity adminEntity);

    /**
     * 添加渠道商信息
     *
     * @param addAdminAgentMainDTO
     * @param adminEntity
     * @return
     */
    R createAgentMain(AddAdminAgentMainDTO addAdminAgentMainDTO, AdminEntity adminEntity);

    /**
     * 编辑渠道商信息
     *
     * @param updateAgentMainDTO
     * @param adminEntity
     * @return
     */
    R updateAgentMain(UpdateAgentMainDTO updateAgentMainDTO, AdminEntity adminEntity);

    /**
     * 开启
     *
     * @param agentProviderId
     * @param adminEntity
     * @return
     */
    R updateAgentProvider(Long agentProviderId, CooperateStatus cooperateStatus, AdminEntity adminEntity);

    /**
     * 查询可以匹配的服务商
     *
     * @param serviceProviderName
     * @param page
     * @return
     */
    R<IPage<AgentMainServiceProviderVO>> queryAgentMainServiceProvider(String serviceProviderName, IPage<AgentMainServiceProviderVO> page);

    /**
     * 添加匹配服务商
     *
     * @param serviceProviderIds
     * @param agentMainId
     * @return
     */
    R addAgentMainServiceProvider(String serviceProviderIds, Long agentMainId, AdminEntity adminEntity);
}

