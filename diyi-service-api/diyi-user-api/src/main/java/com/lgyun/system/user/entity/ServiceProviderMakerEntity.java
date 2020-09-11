package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.ServiceProviderMakerRelType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 服务商创客关联表 Entity
 *
 * @author tzq
 * @since 2020-08-19 16:01:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_service_provider_maker")
public class ServiceProviderMakerEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 关联类型：总包+分包支付关联；众包代开票关联
     */
    private ServiceProviderMakerRelType relType;

}
