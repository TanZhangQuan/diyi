package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CompanyInvoiceState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class TotalMergeInvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付清单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "支付清单")
    private String chargeListUrl;

    @ApiModelProperty(value = "支付回单,过个逗号个开")
    private String enterprisePayReceiptUrl;

    @ApiModelProperty(value = "工单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String worksheetId;

    @ApiModelProperty(value = "支付交付验收单,多个逗号隔开")
    private String acceptPaysheetUrl;

    @ApiModelProperty(value = "价税合计额")
    private String payToPlatformAmount;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "开票状态")
    private CompanyInvoiceState companyInvoiceState;

    @ApiModelProperty(value = "时间")
    private String createTime;

    @ApiModelProperty(value = "开票信息名称")
    private String invoiceEnterpriseName;

    @ApiModelProperty(value = "开票信息纳税人识别号")
    private String invoiceTaxNo;

    @ApiModelProperty(value = "开票信息电话地址")
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
