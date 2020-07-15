package com.lgyun.system.order.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/7/13.
 * @time 15:29.
 */
@Data
@ApiModel(value = "SelfHelpInvoiceDetailsVO对象", description = "SelfHelpInvoiceDetailsVO对象")
public class SelfHelpInvoiceDetailsVO implements Serializable {
    private Long selfHelpInvoiceId;
    private Long runCompanyId;
    private Long addressId;
    private Long invoicePeopleId;
    private Long selfHelpInvoiceDetailId;
    private  Long handPayId;
    private String companyName;
    private String taxNo;
    private String employeeName;
    private String phoneNo;
    private String bankName;
    private  String bankAccount;
    private BigDecimal chargeMoneyNum;
    private  String invoiceType;
    private  String flowContractUrl;
    private  String businessContractUrl;
    private  String addressName;
    private  String addressPhone;
    private  String area;
    private  String city;
    private  String province;
    private  String detailedAddress;
    private  String idCardName;
    private  String idCardNo;
    private  String phoneNumber;
    private Date givePriceDate;
    private BigDecimal totalTaxFee;
    private BigDecimal basicTaxFee;
    private BigDecimal basicTaxFeeRate;
    private BigDecimal invoiceFee;
    private BigDecimal identifyFee;
}