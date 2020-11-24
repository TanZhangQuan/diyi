package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 渠道商-服务商关联表 Entity
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_agent_main_service_provider")
public class AgentMainServiceProviderEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道商ID
     */
    private Long agentMainId;

    /**
     * 服务商id
     */
    private Long serviceProviderId;

    /**
     * 合作状态：合作中，停止合作；首次关联时默认为合作中
     */
    private CooperateStatus cooperateStatus = CooperateStatus.COOPERATING;

    /**
     * 分配人员
     */
    private String matchPerson;

    /**
     * 分配说明
     */
    private String matchDesc;

}
