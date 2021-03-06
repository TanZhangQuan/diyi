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
 * 合伙人-商户关联表 Entity
 *
 * @author tzq
 * @since 2020-11-23 17:38:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_partner_enterprise")
public class PartnerEnterpriseEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 合伙人ID
     */
    private Long partnerId;

    /**
     * 商户ID
     */
    private Long enterpriseId;

    /**
     * 合作关系
     */
    private CooperateType cooperateType;

    /**
     * 合作状态：合作中，停止合作；首次关联时默认为合作中
     */
    private CooperateStatus cooperateStatus = CooperateStatus.COOPERATING;

    /**
     * 操作说明
     */
    private String matchDesc;

}
