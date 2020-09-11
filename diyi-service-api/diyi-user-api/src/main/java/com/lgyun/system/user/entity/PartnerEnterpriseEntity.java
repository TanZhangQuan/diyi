package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 合伙人-商户关联表 Entity
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
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
     * 分配人员
     */
    private String matchPerson;

    /**
     * 分配说明
     */
    private String matchDesc;

}
