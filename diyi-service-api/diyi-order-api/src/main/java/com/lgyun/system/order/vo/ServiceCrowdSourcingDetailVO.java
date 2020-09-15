package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.SelfHelpInvoiceSpApplyState;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/9/14.
 * @time 19:45.
 */
@Data
@ApiModel(value = "ServiceCrowdSourcingDetailVO对象",description = "ServiceCrowdSourcingDetailVO对象")
public class ServiceCrowdSourcingDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 商户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String enterpriseId;
    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String selfHelpInvoiceId;
    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String selfHelpInvoiceApplyId;
    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String selfHelpInvoiceApplyProviderId;
    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String providerSelfHelpInvoiceId;
    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String selfHelpInvoiceDetailId;
    /**
     *服务商id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String serviceProviderId;
    /**
     *价税合计额
     */
    private String chargeMoneyNum;
    /**
     *开票类目
     */
    private String invoiceType;
    /**
     *商户名
     */
    private String enterpriseName;
    /**
     *纳税号
     */
    private String invoiceTaxNo;
    /**
     *地址
     */
    private String invoiceAddress;
    /**
     *电话
     */
    private String invoiceTelNo;
    /**
     *银行开户行
     */
    private String invoiceBankName;
    /**
     *银行卡号
     */
    private String invoiceAccount;
    /**
     *服务商名称
     */
    private String serviceProviderName;
    /**
     *流水回单
     */
    private String flowContractUrl;
    /**
     *交付首验单
     */
    private String deliverSheetUrl;
    /**
     *申请状态
     */
    private SelfHelpInvoiceSpApplyState applyState;
    /**
     *申请时间
     */
    private String createTime;
    /**
     *收件人姓名
     */
    private String addressName;
    /**
     *收件人电话
     */
    private String addressPhone;
    /**
     *省
     */
    private String province;
    /**
     *城市
     */
    private String city;
    /**
     *区
     */
    private String area;
    /**
     *众包发票
     */
    private String invoiceScanPictures;
    /**
     *完税证明
     */
    private String taxScanPictures;

    /**
     * 快递单号
     */
    private String expressNo;
    /**
     * 快递公司
     */
    private String expressCompanyName;
}
