package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.CreateAgentMainDTO;
import com.lgyun.system.user.dto.AgentMainListDTO;
import com.lgyun.system.user.dto.UpdateAgentMainDTO;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.AgentMainEntity;
import com.lgyun.system.user.vo.AgentMainListAdminVO;
import com.lgyun.system.user.vo.AgentMainUpdateDetailVO;

/**
 * 渠道商信息表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
public interface IAgentMainService extends BaseService<AgentMainEntity> {

    /**
     * 根据ID查询渠道商
     *
     * @param agentMainId
     * @return
     */
    int queryCountById(Long agentMainId);

    /**
     * 查询所有渠道商列表
     *
     * @param page
     * @param agentMainListDTO
     * @return
     */
    R<IPage<AgentMainListAdminVO>> getAgentMainListAdmin(AgentMainListDTO agentMainListDTO, IPage<AgentMainListAdminVO> page);

    /**
     * 更改渠道商状态
     *
     * @param agentMainId
     * @param agentMainState
     * @return
     */
    R<String> updateAgentMainState(Long agentMainId, AccountState agentMainState);

    /**
     * 添加渠道商
     *
     * @param createAgentMainDTO
     * @param adminEntity
     * @return
     */
    R<String> createAgentMain(CreateAgentMainDTO createAgentMainDTO, AdminEntity adminEntity);

    /**
     * 编辑渠道商
     *
     * @param updateAgentMainDTO
     * @param adminEntity
     * @return
     */
    R<String> updateAgentMain(UpdateAgentMainDTO updateAgentMainDTO, AdminEntity adminEntity);

    /**
     * 查询编辑渠道商详情
     *
     * @param agentMainId
     * @return
     */
    R<AgentMainUpdateDetailVO> queryAgentMainUpdateDetail(Long agentMainId);

}

