package com.lgyun.system.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class SelfHelpInvoiceExpressByEnterpriseProviderVO implements Serializable {

    @ApiModelProperty(value = "发票")
    private String invoiceScanPictures;

    @ApiModelProperty(value = "快递单号")
    private String expressNo;

    @ApiModelProperty(value = "快递公司")
    private String expressCompanyName;

    @ApiModelProperty(value = "快递信息")
    private Object expressMessage;

}
