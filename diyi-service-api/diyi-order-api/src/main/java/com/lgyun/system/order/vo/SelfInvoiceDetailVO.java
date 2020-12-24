package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class SelfInvoiceDetailVO implements Serializable {

    @ApiModelProperty(value = "自助开票ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String selfHelpInvoiceId;

    @ApiModelProperty(value = "自助开票详情ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String selfHelpInvoiceDetailId;

    @ApiModelProperty(value = "开票类目")
    private String invoiceType;

    @ApiModelProperty(value = "购买商户ID")
    private String purchaseEnterpriseId;

    @ApiModelProperty(value = "购买商户名称")
    private String purchaseEnterpriseName;

    @ApiModelProperty(value = "开票税号")
    private String invoiceTaxNo;

    @ApiModelProperty(value = "地址电话")
    private String invoiceAddressPhone;

    @ApiModelProperty(value = "开票账号")
    private String invoiceBankNameAccount;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "税率")
    private String valueAddedTaxRate;

    @ApiModelProperty(value = "总价税合计额")
    private String chargeMoneyNum;

    @ApiModelProperty(value = "开票人姓名")
    private String invoicePeopleName;

    @ApiModelProperty(value = "身份证号码")
    private String idcardNo;

    @ApiModelProperty(value = "电话号码")
    private String phoneNumber;

    @ApiModelProperty(value = "身份证正面")
    private String idcardPic;

    @ApiModelProperty(value = "身份证反面")
    private String idcardPicBack;

    @ApiModelProperty(value = "业务合同")
    private String businessContractUrl;

    @ApiModelProperty(value = "流水回单")
    private String flowContractUrl;

    @ApiModelProperty(value = "交付支付验收单")
    private String acceptPaysheetCsUrl;

    @ApiModelProperty(value = "发票")
    private String invoiceScanPictures;

    @ApiModelProperty(value = "税票")
    private String taxScanPictures;

    @ApiModelProperty(value = "统一社会信用代码")
    private String ibtaxNo;

    @ApiModelProperty(value = "个体户或个独名称")
    private String ibname;


}
