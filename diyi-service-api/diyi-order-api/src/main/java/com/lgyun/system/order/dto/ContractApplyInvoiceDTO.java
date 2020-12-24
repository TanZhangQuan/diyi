package com.lgyun.system.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class ContractApplyInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商ID")
    @NotNull(message = "请选择服务商")
    private Long serviceProviderId;

    @ApiModelProperty(value = "支付清单ID")
    @NotBlank(message = "请选择支付清单")
    private String payEnterpriseIds;

    @ApiModelProperty(value = "开票类目ID")
    @NotNull(message = "请选择开票类目")
    private Long invoiceCatalogId;

    @ApiModelProperty(value = "说明")
    private String applicationDesc;

}
