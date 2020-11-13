package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CompanyInvoiceState;
import lombok.Data;

/**
 * 分包-门征
 * @author tzq
 * @date 2020/8/14.
 * @time 14:31.
 */
@Data
public class EnterpriseSubcontractPortalVO {
    private static final long serialVersionUID = 1L;

    /**
     * 支付清单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    /**
     * 创客支付id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payMakerId;

    /**
     * 服务商ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerInvoiceId;

    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerTaxRecordId;

    /**
     * 发票编号
     */
    private String voiceSerialNo;

    /**
     * 发票地址
     */
    private String makerVoiceUrl;

    /**
     * 完税证明
     */
    private String makerTaxUrl;

    /**
     * 开票状态
     */
    private CompanyInvoiceState companyInvoiceState;

    /**
     * 服务商名字
     */
    private String serviceProviderName;

    /**
     * 开票时间
     */
    private String invoicePrintDate;

    /**
     * 商户名称
     */
    private String enterpriseName;
    /**
     * 商户支付清单url
     */
    private String chargeListUrl;
    /**
     * 工单号
     */
    private String worksheetId;
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
}
