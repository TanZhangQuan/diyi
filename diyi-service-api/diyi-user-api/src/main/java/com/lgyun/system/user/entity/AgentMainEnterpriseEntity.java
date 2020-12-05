package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.CooperateType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 渠道商户表 Entity
 *
 * @author tzq
 * @since 2020-11-23 14:39:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_agent_main_enterprise")
public class AgentMainEnterpriseEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道商ID
     */
    private Long agentMainId;

    /**
     * 商户ID
     */
    private Long enterpriseId;

    /**
     * 合作关系
     */
    private CooperateType cooperateType;

    /**
     * 合作状态
     */
    private CooperateStatus cooperateStatus = CooperateStatus.COOPERATING;

    /**
     * 操作说明
     */
    private String matchDesc;
}
