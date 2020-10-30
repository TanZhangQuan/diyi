package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.MakerInvoiceType;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
import com.lgyun.common.enumeration.PayEnterprisePayState;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tzq
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
public class PayEnterpriseDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总包支付清单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 总包支付清单URL
     */
    private String chargeListUrl;

    /**
     * 总包支付回单图片(多张逗号隔开)
     */
    private String enterprisePayReceiptUrls;

    /**
     * 工单编号
     */
    private String worksheetId;

    /**
     * 总包+分包交付支付验收单
     */
    private String acceptPaysheetUrl;

    /**
     * 审核状态
     */
    private PayEnterpriseAuditState auditState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创客发票开票类别
     */
    private MakerInvoiceType makerInvoiceType;

    /**
     * 支付状态
     */
    private PayEnterprisePayState payState;

    /**
     * 总包开票状态：未开，已开
     */
    private InvoiceState companyInvoiceState;

    /**
     * 分包开票状态：未开，已开
     */
    private InvoiceState subcontractingInvoiceState;

    /**
     * 企业总支付额价税合计总额
     */
    private BigDecimal payToPlatformAmount;

}
