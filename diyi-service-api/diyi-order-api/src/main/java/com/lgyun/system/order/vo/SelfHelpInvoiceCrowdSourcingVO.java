package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoicePrintState;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/8/19.
 * @time 16:06.
 */
@Data
public class SelfHelpInvoiceCrowdSourcingVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 自助开票iD
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceId;

    /**
     * 自助开票申请iD
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceApplyId;

    /**
     * 自助开票服务商ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceApplyProviderId;

    /**
     *服务商自助开票明细Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long providerSelfHelpInvoiceId;

    /**
     * 自助开票详情ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceDetailId;

    /**
     * 服务商Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    /**
     * 商户名字
     */
    private String enterpriseName;

    /**
     * 服务商名字
     */
    private String serviceProviderName;

    /**
     * 清单
     */
    private String listFile;

    /**
     * 发票可能会多张，隔开
     */
    private String invoiceScanPictures;

    /**
     * 完税证明发票可能会多张，隔开
     */
    private String taxScanPictures;

    /**
     * 开票日期
     */
    private String updateDatetime;

    /**
     * 开票状态
     */
    private InvoicePrintState invoicePrintState;

    /**
     *快递单号
     */
    private String expressSheetNo;

    /**
     *快递公司
     */
    private String expressCompanyName;
}
