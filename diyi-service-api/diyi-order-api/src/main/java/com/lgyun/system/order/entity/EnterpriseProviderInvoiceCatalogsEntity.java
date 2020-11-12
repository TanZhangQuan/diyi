package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.InvoiceDemand;
import com.lgyun.common.enumeration.SetType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 商户-服务商开票类目表 Entity
 *
 * @author liangfeihu
 * @since 2020-11-12 17:54:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_enterprise_provider_invoice_catalogs")
public class EnterpriseProviderInvoiceCatalogsEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private Long enterpriseId;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

    /**
     * 发票类目名称
     */
    private String invoiceCatalogName;

    /**
     * 设置人员
     */
    private String setPerson;

    /**
     * 设置性质
     */
    private SetType setType;

    /**
     * 开票诉求
     */
    private InvoiceDemand invoiceDemand;

    /**
     * 开票诉求备注
     */
    private String invoiceDemandDesc;

}
