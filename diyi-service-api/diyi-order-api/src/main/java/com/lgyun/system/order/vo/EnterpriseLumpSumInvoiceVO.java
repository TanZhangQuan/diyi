package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceState;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 总包
 * @author .
 * @date 2020/8/11.
 * @time 9:21.
 */
@Data
@ApiModel(value = "EnterpriseLumpSumInvoiceVO对象",description = "EnterpriseLumpSumInvoiceVO对象")
public class EnterpriseLumpSumInvoiceVO {
    private static final long serialVersionUID = 1L;

    /**
     * 支付清单Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    /**
     *开票ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoicePrintId;

    /**
     * 商户Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 服务商Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    /**
     * 开篇申请id,没有就是没有申请
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long applicationId;

    /**
     *商户名字
     */
    private String enterpriseName;

    /**
     * 服务商名字
     */
    private String serviceProviderName;

    /**
     * 支付清单url
     */
    private String chargeListUrl;

    /**
     * 关联的工单号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksheetId;

    /**
     * 发票编码
     */
    private String invoiceSerialNo;

    /**
     * 发票url
     */
    private String companyInvoiceUrl;

    /**
     *
     * 开票状态
     */
    private InvoiceState companyInvoiceState;

    /**
     * 开篇日期
     */
    private Date invoicePrintDate;

    /**
     * 快递单号
     */
    private String expressSheetNo;

    /**
     *  快递公司
     */
    private String expressCompanyName;

    /**
     * 支付回单
     */
    private String enterprisePayReceiptUrl;

    /**
     * 快鸟快递
     */
    private String KOrderTracesByJson;
}
