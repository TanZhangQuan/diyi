package com.lgyun.system.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class InvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开票资料-公司名称")
    private String invoiceEnterpriseName;

    @ApiModelProperty(value = "开票资料-税号")
    private String invoiceTaxNo;

    @ApiModelProperty(value = "开票资料-地址和电话")
    private String invoiceAddressPhone;

    @ApiModelProperty(value = "开票资料-开户银行和账号")
    private String invoiceBankNameAccount;

}
