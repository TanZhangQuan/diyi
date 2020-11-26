package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.MakerInvoiceType;
import lombok.Data;

/**
 * 分包 -汇总
 *
 * @author .
 * @date 2020/8/14.
 * @time 9:47.
 */
@Data
public class EnterpriseSubcontractInvoiceVO {
    private static final long serialVersionUID = 1L;

    /**
     * 总包支付清单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    /**
     * 汇总代开发票ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerTotalInvoiceId;

    /**
     * 服务商ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    /**
     * 发票编码
     */
    private String invoiceSerialNo;

    /**
     * 总包发票URL
     */
    private String companyInvoiceUrl;

    /**
     * 完税证明Url
     */
    private String makerTaxUrl;

    /**
     * 开票状态
     */
    private InvoiceState subcontractingInvoiceState;

    /**
     * 服务商名字
     */
    private String serviceProviderName;

    /**
     * 申请日期
     */
    private String invoicePrintDate;

    /**
     * 商户名称
     */
    private String enterpriseName;
    /**
     * 总包支付清单
     */
    private String chargeListUrl;
    /**
     * 工单号
     */
    private String worksheetId;
    /**
     * 开票日期
     */
    private String companyVoiceUploadDatetime;
    /**
     * 交付支付验收单
     */
    private String acceptPaysheetUrl;

    /**
     * 交付支付验收单Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String acceptPaysheetId;

    /**
     * 支付回单
     */
    private String enterprisePayReceiptUrl;

    /**
     * 创客发票开票类别: 自然人汇总代开；自然人门征单开；个体户税务局代开；个体户自开；个独自开
     */
    private MakerInvoiceType makerInvoiceType;
}
