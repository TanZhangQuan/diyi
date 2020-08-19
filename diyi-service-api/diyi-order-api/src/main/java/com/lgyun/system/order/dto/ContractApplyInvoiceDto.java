package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "支付清单id不能为空")
    private Long payEnterpriseId;
    /**
     * 开票总金额
     */
    @NotNull(message = "开票总金额不能为空")
    @Min(0)
    private BigDecimal voiceTotalAmount;
    /**
     * 开票类目Id
     */
    @NotNull(message = "开票类目Id不能为空")
    private Long invoiceCatalogId;
    /**
     * 说明
     */
    private String applicationDesc;
}
