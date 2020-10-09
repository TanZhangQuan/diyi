package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceState;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务商查看分包发票详情
 * @author .
 * @date 2020/9/7.
 * @time 18:22.
 */
@Data
public class InvoiceServiceSubDetailsVO implements Serializable {
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
     * 地址
     */
    private String invoiceAddress;

    /**
     * 公司电话
     */
    private String invoiceTelNo;

    /**
     * 开票银行
     */
    private String invoiceBankName;

    /**
     * 开票银行账户
     */
    private String invoiceAccountName;

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
     * 开票状态
     */
    private InvoiceState subcontractingInvoiceState;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 支付回单
     */
    private String enterprisePayReceiptUrl;
}
