package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.AddOrUpdateAgentMainContactDTO;
import com.lgyun.system.user.entity.AgentPersonEntity;

/**
 * 渠道商人员表 Service 接口
 *
 * @author liangfeihu
 * @since 2020-10-20 18:25:03
 */
public interface IAgentPersonService extends BaseService<AgentPersonEntity> {
    /**
     * 根据手机号码查询渠道商员工是否存在
     *
     * @param phoneNumber
     * @return
     */
    Integer findCountByPhoneNumber(String phoneNumber);

    /**
     * 根据手机号码查询渠道商员工
     *
     * @param phoneNumber
     * @return
     */
    AgentPersonEntity findByPhoneNumber(String phoneNumber);

    /**
     * 添加或修改渠道商联系人
     *
     * @param addOrUpdateAgentMainContactDTO
     * @param agentMainId
     * @return
     */
    R<String> addOrUpdateEnterpriseContact(AddOrUpdateAgentMainContactDTO addOrUpdateAgentMainContactDTO, Long agentMainId);
}

