package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.MakerInvoiceType;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务商查询总包发票详情
 * @author .
 * @date 2020/9/5.
 * @time 14:26.
 */
@Data
public class InvoiceServiceDetailSummaryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付清单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    /**
     * 商户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 价税合计额
     */
    private String payToPlatformAmount;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 纳税号码
     */
    private String invoiceTaxNo;

    /**
     * 开票资料-地址和电话
     */
    private String invoiceAddressPhone;

    /**
     * 开票资料-开户银行和账号
     */
    private String invoiceBankNameAccount;

    /**
     * 服务商名字
     */
    private String serviceProviderName;

    /**
     * 支付清单url
     */
    private String chargeListUrl;

    /**
     * 工单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String worksheetId;

    /**
     * 分包开票状态
     */
    private InvoiceState subcontractingInvoiceState;

    /**
     * 创建时间
     */
    private String createTime;


    /**
     * 发票url
     */
    private String companyInvoiceUrl;

    /**
     * 支付回单
     */
    private String enterprisePayReceiptUrl;

    /**
     *税票
     */
    private String makerTaxUrl;

    /**
     *创客发票开票类别: 自然人汇总代开；自然人门征单开；个体户税局代开；个体户自开；个独自开
     */
    private MakerInvoiceType makerInvoiceType;

}
