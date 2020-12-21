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
     *自助开票id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String selfHelpInvoiceId;
//    /**
//     *
//     */
//    @JsonSerialize(using = ToStringSerializer.class)
//    private String selfHelpInvoiceApplyId;
    /**
     *自助开票详情id
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
     *开票类目
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
     *开票税号
     */
    private String invoiceTaxNo;
    /**
     *地址电话
     */
    private String invoiceAddressPhone;
    /**
     *开票账号
     */
    private String invoiceBankNameAccount;
    /**
     *
     */
    private String projectName;
    /**
     *税率
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
     *电话号码
     */
    private String phoneNumber;
    /**
     *身份证正面
     */
    private String idcardPic;
    /**
     *反
     */
    private String idcardPicBack;
    /**
     *业务合同
     */
    private String businessContractUrl;
    /**
     *回单
     */
    private String flowContractUrl;
    /**
     *验收单
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

    /**
     * 发票
     */
    private String invoiceScanPictures;

    /**
     * 税票
     */
    private String taxScanPictures;


    /**
     *统一社会信用代码
     */
    private String ibtaxNo;

    /**
     * 个体户或个独名称
     */
    private String ibname;


}
