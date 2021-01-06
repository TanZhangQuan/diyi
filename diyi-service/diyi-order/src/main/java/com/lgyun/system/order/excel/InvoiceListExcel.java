package com.lgyun.system.order.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * UserDTO
 *
 * @author tzq
 * @since 2020/6/6 22:12
 */
@Data
public class InvoiceListExcel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty("付款单位")
    private String payer;

    @ExcelProperty("税号")
    private String paragraph;

    @ExcelProperty("地址、电话")
    private String addressTelephone;

    @ExcelProperty("开户行账号")
    private String bankAccountNumber;

    @ExcelProperty("项目")
    private String projectName;

    @ExcelProperty("数量")
    private Integer num;

    @ExcelProperty("单位")
    private String company;

    @ExcelProperty("单价")
    private BigDecimal unitPrice;

    @ExcelProperty("税率3%/1%")
    private Integer taxRate;

    @ExcelProperty("发票价税合计填写该列，开票额")
    private BigDecimal taxTotalprice;

    @ExcelProperty("开票人姓名")
    private String invoicePeopleName;

    @ExcelProperty("身份证号码")
    private String idcardNo;

    @ExcelProperty("手机号码")
    private String phoneNumber;

    @ExcelProperty("发票类别普票OR专票")
    private String invoiceCategory;

    @ExcelProperty("个体户名称")
    private String individualBusinessName;

    @ExcelProperty("个体户统一社会信用代码")
    private String socialCreditCode;

    @ExcelProperty("个体户经营者（法人）姓名")
    private String legalPersonName;

    @ExcelProperty("个体户经营者身份证号码")
    private String operatorIdCard;

    @ExcelProperty("个人独资企业名称")
    private String aloneName;

    @ExcelProperty("个独统一社会信用代码")
    private String aloneSocialCreditCode;

    @ExcelProperty("个独经营者（法人）姓名")
    private String aloneLegalPersonName;

    @ExcelProperty("个独经营者身份证号码")
    private String aloneOperatorIdCard;

    @ExcelProperty("身份证正面url")
    private String positiveIdCard;

    @ExcelProperty("身份证反面url")
    private String backIdCard;

    @ExcelProperty("业务合同")
    private String businessContract;

    @ExcelProperty("支付回单")
    private String paymentReceipt;

    @ExcelProperty("交付支付验收单")
    private String paymentAcceptance;

//    @ExcelProperty("统一社会信用代码")
//    private String ibtaxNo;
//
//    @ExcelProperty("公司名称")
//    private String corporateName;
//
//    @ExcelProperty("图片")
//    private String tupian;

//    @ExcelIgnore
//    @ExcelProperty("对象")
//    private SelfHelpInvoiceDTO selfHelpInvoiceDto;
}
