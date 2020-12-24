package com.lgyun.system.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class DoorSignInvoiceDTO implements Serializable {

    @ApiModelProperty(value = "支付清单(多个逗号隔开)")
    private String payEnterpriseIds;

    @ApiModelProperty(value = "XXXXX")
    private String doorSignInvoiceJson;
}
