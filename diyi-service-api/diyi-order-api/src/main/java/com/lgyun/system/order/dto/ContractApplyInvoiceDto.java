package com.lgyun.system.order.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jun.
 * @date 2020/8/12.
 * @time 17:48.
 */
@Data
public class ContractApplyInvoiceDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 支付清单id
     */
    private Long payEnterpriseId;
    /**
     * 开票总金额
     */
    private BigDecimal voiceTotalAmount;
    /**
     * 开票类目Id
     */
    private Long invoiceCatalogId;
    /**
     * 说明
     */
    private String applicationDesc;
}
