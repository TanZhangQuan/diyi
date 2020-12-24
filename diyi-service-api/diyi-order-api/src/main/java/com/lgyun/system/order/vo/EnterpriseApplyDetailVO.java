package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class EnterpriseApplyDetailVO implements Serializable {


    @ApiModelProperty(value = "总包申请ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoiceApplicationId;

    @ApiModelProperty(value = "总包支付清单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "支付清单")
    private String chargeListUrl;

    @ApiModelProperty(value = "支付回单(多张)")
    private String enterprisePayReceiptUrl;

    @ApiModelProperty(value = "工单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String worksheetId;

    @ApiModelProperty(value = "交付支付验收单(多张)")
    private String acceptPaysheetUrl;

    @ApiModelProperty(value = "申请状态")
    private String applyState;

    @ApiModelProperty(value = "申请说明")
    private String applicationDesc;

    @ApiModelProperty(value = "发票类目")
    private String invoiceCatalogName;

    @ApiModelProperty(value = "价税合计额")
    private String payToPlatformAmount;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "申请状态")
    private String applicationState;

    @ApiModelProperty(value = "申请时间")
    private String createTime;

    @ApiModelProperty(value = "开票信息名称")
    private String invoiceEnterpriseName;

    @ApiModelProperty(value = "开票信息纳税人识别号")
    private String invoiceTaxNo;

    @ApiModelProperty(value = "开票信息电话是地址")
    private String invoiceAddressPhone;

    @ApiModelProperty(value = "开票信息银行卡号")
    private String invoiceBankNameAccount;

    @ApiModelProperty(value = "收件人姓名")
    private String addressName;

    @ApiModelProperty(value = "收件人电话")
    private String addressPhone;

    @ApiModelProperty(value = "收件人地区")
    private String area;

    @ApiModelProperty(value = "收件人城市")
    private String city;

    @ApiModelProperty(value = "收件人省份")
    private String province;

    @ApiModelProperty(value = "收件人收人详细地址")
    private String detailedAddress;
}
