package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/12/11.
 * @time 16:36.
 */
@Data
public class SelfInvoiceDetailVo implements Serializable {

    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String selfHelpInvoiceId;
//    /**
//     *
//     */
//    @JsonSerialize(using = ToStringSerializer.class)
//    private String selfHelpInvoiceApplyId;
    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String selfHelpInvoiceDetailId;
//    /**
//     *
//     */
//    private String enterpriseName;
//    /**
//     *
//     */
//    private CrowdSourcingPayType payType;
    /**
     *
     */
    private String invoiceType;
//    /**
//     *
//     */
//    private SelfHelpInvoiceApplyState applyState;
    /**
     *
     */
    private String purchaseEnterpriseId;
    /**
     *
     */
    private String purchaseEnterpriseName;
    /**
     *
     */
    private String invoiceTaxNo;
    /**
     *
     */
    private String invoiceAddressPhone;
    /**
     *
     */
    private String invoiceBankNameAccount;
    /**
     *
     */
    private String projectName;
    /**
     *
     */
    private String valueAddedTaxRate;
    /**
     *
     */
    private String chargeMoneyNum;
    /**
     *
     */
    private String invoicePeopleName;
    /**
     *
     */
    private String idcardNo;
    /**
     *
     */
    private String phoneNumber;
    /**
     *
     */
    private String idcardPic;
    /**
     *
     */
    private String idcardPicBack;
    /**
     *
     */
    private String businessContractUrl;
    /**
     *
     */
    private String flowContractUrl;
    /**
     *
     */
    private String acceptPaysheetCsUrl;
//    /**
//     *
//     */
//    private String addressName;
//    /**
//     *
//     */
//    private String addressPhone;
//    /**
//     *
//     */
//    private String province;
//    /**
//     *
//     */
//    private String city;
//    /**
//     *
//     */
//    private String area;
//    /**
//     *
//     */
//    private String detailedAddress;
//    /**
//     *
//     */
//    private String totalPayProviderFee;
//    /**
//     *
//     */
//    private String serviceAndTaxMoney;
//    /**
//     *
//     */
//    private String serviceFee;
//    /**
//     *
//     */
//    private String serviceRate;
//    /**
//     *
//     */
//    private String serviceTax;
//    /**
//     *
//     */
//    private String serviceInvoiceFee;
//    /**
//     *
//     */
//    private String idendityConfirmFee;
//    /**
//     *
//     */
//    private String payCertificate;
//
//    /**
//     *
//     */
//    private String accountName;
//
//    /**
//     *
//     */
//    private String accountNo;
//
//    /**
//     *
//     */
//    private String accountBank;
//
//    /**
//     *
//     */
//    private String basicAccountBank;

    private String invoiceScanPictures;

    private String taxScanPictures;
}
