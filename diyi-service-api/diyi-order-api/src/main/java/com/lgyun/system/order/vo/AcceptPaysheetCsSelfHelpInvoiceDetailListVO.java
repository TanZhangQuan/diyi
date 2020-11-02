package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class AcceptPaysheetCsSelfHelpInvoiceDetailListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自主开票明细ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 创客/开票人姓名
     */
    private String name;

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
