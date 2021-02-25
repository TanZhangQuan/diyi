package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoicePrintState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class SelfHelpInvoiceDetailAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自助开票主表ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceId;

    @ApiModelProperty(value = "XXXXX")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceDetailId;

    @ApiModelProperty(value = "自助开票服务商ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceApplyProviderId;

    @ApiModelProperty(value = "自助开票服务商明细ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceApplyProviderDetailId;

    @ApiModelProperty(value = "自助开票快递ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceExpressId;

    @ApiModelProperty(value = "开票人")
    private String invoicePeopleName;

    @ApiModelProperty(value = "开票人身份证")
    private String idcardNo;

    @ApiModelProperty(value = "开票人电话")
    private String phoneNumber;

    @ApiModelProperty(value = "价税合计额")
    private String chargeMoneyNum;

    @ApiModelProperty(value = "服务税率")
    private String serviceRate;

    @ApiModelProperty(value = "支付回单")
    private String flowContractUrl;

    @ApiModelProperty(value = "业务合同(多张)")
    private String businessContractUrl;

    @ApiModelProperty(value = "发票(多张)")
    private String invoiceScanPictures;

    @ApiModelProperty(value = "税票")
    private String  taxScanPictures;

    @ApiModelProperty(value = "快递公司")
    private String expressCompanyName;

    @ApiModelProperty(value = "快递单号")
    private String expressNo;

    @ApiModelProperty(value = "开票状态")
    private InvoicePrintState invoicePrintState;

}
