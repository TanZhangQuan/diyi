package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.Max;
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
    @NotNull(message = "请输入开票总金额")
    @Min(value = 0, message = "开票总金额不能小于0")
    @Max(value = 999999999, message = "开票总金额不能大于999999999")
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
