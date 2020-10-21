package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.admin.AddAdminAgentMainDTO;
import com.lgyun.system.user.dto.admin.QueryAgentMainDTO;
import com.lgyun.system.user.dto.admin.UpdateAgentMainDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.AgentMainEntity;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.admin.AdminAgentMainVO;

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
    R updateIllegal(Long agentMainId, AdminEntity adminEntity);

    /**
     * 冻结
     *
     * @param agentMainId
     * @return
     */
    R updateFreeze(Long agentMainId, AdminEntity adminEntity);

    /**
     * 正常
     *
     * @param agentMainId
     * @return
     */
    R updateNormal(Long agentMainId, AdminEntity adminEntity);

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
}

