package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CrowdSourcingPayType;
import com.lgyun.common.enumeration.SelfHelpInvoiceApplyState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class SelfHelpInvoiceSingleByEnterpriseVO implements Serializable {

    @ApiModelProperty(value = "自助开票ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "开票清单文件")
    private String listFile;

    @ApiModelProperty(value = "众包支付模式")
    private CrowdSourcingPayType payType;

    @ApiModelProperty(value = "开票类目")
    private String invoiceTypes;

    @ApiModelProperty(value = "总需支付服务商税费=总服务税费+总开票手续费+总身份验证费，自动计算")
    private BigDecimal totalPayProviderFee;

    @ApiModelProperty(value = "业务合同(多张)")
    private String businessContractUrls;

    @ApiModelProperty(value = "支付回单(多张)")
    private String flowContractUrls;

    @ApiModelProperty(value = "验收单(多张)")
    private String acceptPaysheetUrls;

    @ApiModelProperty(value = "申请状态")
    private SelfHelpInvoiceApplyState currentState;

    @ApiModelProperty(value = "开票时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "开票资料-公司名称")
    private String invoiceEnterpriseName;

    @ApiModelProperty(value = "开票资料-税号")
    private String invoiceTaxNo;

    @ApiModelProperty(value = "开票资料-地址")
    private String invoiceAddress;

    @ApiModelProperty(value = "开票资料-电话")
    private String invoiceTelNo;

    @ApiModelProperty(value = "开票资料-开户银行")
    private String invoiceBankName;

    @ApiModelProperty(value = "开票资料-账号")
    private String invoiceAccount;

    @ApiModelProperty(value = "收件人")
    private String addressName;

    @ApiModelProperty(value = "手机号码")
    private String addressPhone;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "总服务税费=总服务外包费*服务税费率")
    private BigDecimal serviceAndTaxMoney;

    @ApiModelProperty(value = "总服务外包费")
    private BigDecimal serviceFee;

    @ApiModelProperty(value = "服务税费率")
    private BigDecimal serviceRate;

    @ApiModelProperty(value = "总税费，一般不填")
    private BigDecimal serviceTax;

    @ApiModelProperty(value = "总开票手续费")
    private BigDecimal serviceInvoiceFee;

    @ApiModelProperty(value = "总身份验证费")
    private BigDecimal idendityConfirmFee;

    @ApiModelProperty(value = "账户名称")
    private String accountName;

    @ApiModelProperty(value = "银行账号")
    private String accountNo;

    @ApiModelProperty(value = "开户银行")
    private String accountBank;

}
