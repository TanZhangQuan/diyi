package com.lgyun.system.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class InvoiceListExcelDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "付款单位")
    private String payer;

    @ApiModelProperty(value = "税号")
    private String paragraph;

    @ApiModelProperty(value = "地址、电话")
    private String addressTelephone;

    @ApiModelProperty(value = "开户行账号")
    private String bankAccountNumber;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "数量")
    private String num;

    @ApiModelProperty(value = "单位")
    private String company;

    @ApiModelProperty(value = "单价")
    private BigDecimal unitPrice;

    @ApiModelProperty(value = "税率")
    private BigDecimal taxRate;

    @ApiModelProperty(value = "发票价税合计填写该列，开票额")
    private BigDecimal taxTotalprice;

    @ApiModelProperty(value = "开票人姓名")
    private String invoicePeopleName;

    @ApiModelProperty(value = "身份证号码")
    private String idcardNo;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "发票类别普票OR专票")
    private String invoiceCategory;

    @ApiModelProperty(value = "个体户名称")
    private String individualBusinessName;

    @ApiModelProperty(value = "个体户统一社会信用代码")
    private String socialCreditCode;

    @ApiModelProperty(value = "个体户经营者（法人）姓名")
    private String legalPersonName;

    @ApiModelProperty(value = "个体户经营者身份证号码")
    private String operatorIdCard;

    @ApiModelProperty(value = "个人独资企业名称")
    private String aloneName;

    @ApiModelProperty(value = "个独统一社会信用代码")
    private String aloneSocialCreditCode;

    @ApiModelProperty(value = "个独经营者（法人）姓名")
    private String aloneLegalPersonName;

    @ApiModelProperty(value = "个体户经营者身份证号码")
    private String aloneOperatorIdCard;

    @ApiModelProperty(value = "身份证正面")
    private String positiveIdCard;

    @ApiModelProperty(value = "身份证反面")
    private String backIdCard;

    @ApiModelProperty(value = "业务合同")
    private String businessContract;

    @ApiModelProperty(value = "支付回单")
    private String paymentReceipt;

    @ApiModelProperty(value = "交付支付验收单")
    private String paymentAcceptance;
}
