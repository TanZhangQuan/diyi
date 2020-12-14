package com.lgyun.system.order.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/12/9.
 * @time 19:16.
 */
@Data
public class InvoiceListExcelDto implements Serializable {
    //("付款单位")
    private String payer;

    //("税号")
    private String paragraph;

    //("地址、电话")
    private String addressTelephone;

    //("开户行账号")
    private String bankAccountNumber;

    //("项目")
    private String projectName;

    //("数量")
    private String num;

    //("单位")
    private String company;

    //("单价")
    private String unitPrice;

    //("税率")
    private String taxRate;

    //("发票价税合计填写该列，开票额")
    private String taxTotalprice;

    //("开票人姓名")
    private String invoicePeopleName;

    //("身份证号码")
    private String idcardNo;

    //("手机号码")
    private String phoneNumber;

    //("发票类别普票OR专票")
    private String invoiceCategory;

    //("个体户名称")
    private String individualBusinessName;

    //("个体户统一社会信用代码")
    private String socialCreditCode;

    //("个体户经营者（法人）姓名")
    private String legalPersonName;

    //("个体户经营者身份证号码")
    private String operatorIdCard;

    //("个人独资企业名称")
    private String aloneName;

    //("个独统一社会信用代码")
    private String aloneSocialCreditCode;

    //("个独经营者（法人）姓名")
    private String aloneLegalPersonName;

    //("个体户经营者身份证号码")
    private String aloneOperatorIdCard;

    //("身份证正面url")
    private String positiveIdCard;

    //("身份证反面url")
    private String backIdCard;

    //("业务合同")
    private String businessContract;

    //("支付回单")
    private String paymentReceipt;

    //("交付支付验收单")
    private String paymentAcceptance;
}
