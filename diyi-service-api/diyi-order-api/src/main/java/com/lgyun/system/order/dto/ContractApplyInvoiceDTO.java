package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tzq
 * @date 2020/8/12.
 * @time 17:48.
 */
@Data
public class ContractApplyInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商id
     */
    @NotNull(message = "请选择服务商")
    private Long serviceProviderId;

    /**
     * 支付清单id
     */
    @NotBlank(message = "请选择支付清单")
    private String payEnterpriseIds;


    /**
     * 开票类目Id
     */
    @NotNull(message = "请选择开票类目")
    private Long invoiceCatalogId;

    /**
     * 说明
     */
    private String applicationDesc;

}
