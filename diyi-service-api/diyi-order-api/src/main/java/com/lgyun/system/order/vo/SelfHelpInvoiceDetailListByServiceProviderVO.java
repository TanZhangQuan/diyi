package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CrowdSourcingPayType;
import com.lgyun.common.enumeration.InvoicePrintState;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/8/3.
 * @time 15:36.
 */
@Data
@ApiModel(value = "SelfHelpInvoiceListByServiceProviderVO对象", description = "SelfHelpInvoiceListByServiceProviderVO对象")
public class SelfHelpInvoiceDetailListByServiceProviderVO implements Serializable {

    /**
     * 自助开票明细id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 众包支付模式：标准支付；扩展支付；商户代付税费
     */
    private CrowdSourcingPayType payType;

    /**
     * 开票类目
     */
    private String invoiceType;

    /**
     * 价税合计额
     */
    private BigDecimal chargeMoneyNum;

    /**
     * 业务合同URL
     */
    private String businessContractUrl;

    /**
     * 支付回单URL
     */
    private String flowContractUrl;

    /**
     * 验收单URL
     */
    private String acceptPaysheetUrl;

    /**
     * 开票状态：待申请；申请中；开票中；已开票；开票失败
     */
    private InvoicePrintState invoicePrintState;

    /**
     * 开票时间
     */
    private Date invoiceDate;
}
