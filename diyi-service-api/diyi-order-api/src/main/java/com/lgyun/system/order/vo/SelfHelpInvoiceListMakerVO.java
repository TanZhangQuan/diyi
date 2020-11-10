package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoicePrintState;
import com.lgyun.common.enumeration.MakerType;
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
public class SelfHelpInvoiceListMakerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 开票人身份类别 1，自然人；2，个体户；3，个独；4，合伙企业；5，有限公司
     */
    private MakerType makerType;

    /**
     * 个体户/个独ID
     */
    private String individualName;

    /**
     * 开票状态：待申请；申请中；开票中；已开票；开票失败
     */
    private InvoicePrintState invoicePrintState;

    /**
     * 开票类目
     */
    private String invoiceType;

    /**
     * 增值税税率
     */
    private BigDecimal valueAddedTaxRate;

    /**
     * 价税合计额
     */
    private BigDecimal chargeMoneyNum;

    /**
     * 开票手续费
     */
    private BigDecimal serviceInvoiceFee;

    /**
     * 身份验证费
     */
    private BigDecimal idendityConfirmFee;

    /**
     * 需支付服务商税费=价税合计额*服务税费率+开票手续费+身份验证费
     */
    private BigDecimal payProviderFee;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
