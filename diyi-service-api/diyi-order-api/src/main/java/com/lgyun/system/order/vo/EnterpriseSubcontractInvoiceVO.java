package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
     * 商户支付清单ID
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
    private String companyInvoiceState;

    /**
     * 服务商名字
     */
    private String serviceProviderName;

    /**
     * 开票日期
     */
    private String invoicePrintDate;
}
