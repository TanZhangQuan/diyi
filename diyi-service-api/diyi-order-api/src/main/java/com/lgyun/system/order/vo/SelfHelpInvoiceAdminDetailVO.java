package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class SelfHelpInvoiceAdminDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自主开票主表ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceId;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "自主开票费用ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceFeeId;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "纳税号")
    private String invoiceTaxNo;

    @ApiModelProperty(value = "开票地址")
    private String invoiceAddress;

    @ApiModelProperty(value = "开票电话")
    private String invoiceTelNo;

    @ApiModelProperty(value = "发票银行卡")
    private String invoiceBankName;

    @ApiModelProperty(value = "发票银行卡号")
    private String invoiceAccount;

    @ApiModelProperty(value = "开票清单")
    private String listFile;

    @ApiModelProperty(value = "众包支付模式")
    private String payType;

    @ApiModelProperty(value = "价税合计额")
    private String totalChargeMoneyNum;

    @ApiModelProperty(value = "总需支付服务商税费")
    private String totalPayProviderFee;

    @ApiModelProperty(value = "总服务税费")
    private String serviceAndTaxMoney;

    @ApiModelProperty(value = "总服务外包费")
    private String serviceFee;

    @ApiModelProperty(value = "服务税费率")
    private String serviceRate;

    @ApiModelProperty(value = "总税费，一般不填")
    private String serviceTax;

    @ApiModelProperty(value = "总开票手续费")
    private String serviceInvoiceFee;

    @ApiModelProperty(value = "总身份验证费")
    private String idendityConfirmFee;

    @ApiModelProperty(value = "状态")
    private String currentState;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "收件人姓名")
    private String addressName;

    @ApiModelProperty(value = "收件人电话")
    private String addressPhone;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "户名")
    private String accountName;

    @ApiModelProperty(value = "账号")
    private String accountNo;

    @ApiModelProperty(value = "开户行")
    private String accountBank;

    @ApiModelProperty(value = "基本存款账号")
    private String basicAccountBank;
}
