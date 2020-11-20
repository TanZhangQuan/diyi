package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.ApplyScope;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 商户-服务商开票类目表 Entity
 *
 * @author tzq
 * @since 2020-11-12 17:54:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_service_provider_invoice_catalogs")
public class ServiceProviderInvoiceCatalogsEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

    /**
     * 应用范围
     */
    private ApplyScope applyScope;

    /**
     * 发票类目名称
     */
    private String invoiceCatalogName;

    /**
     * 设置人员
     */
    private String setPerson;

    /**
     * 设置说明
     */
    private String setDesc;

}
