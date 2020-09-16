package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商户-服务商开票类目：记录商户在特定服务商的开票类目 Entity
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_enterprise_service_provider_invoice_catalogs")
public class EnterpriseServiceProviderInvoiceCatalogsEntity extends BaseEntity {
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
     * 设置日期
     */
    private Date setDate;

    /**
     * 设置人员
     */
    private String setPerson;

    /**
     * 设置性质
     */
    private String setType;

}
