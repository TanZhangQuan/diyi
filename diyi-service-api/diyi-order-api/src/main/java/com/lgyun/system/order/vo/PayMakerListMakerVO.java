package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.PayMakerPayState;
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
public class PayMakerListMakerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客支付明细ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 创客身份: 自然人，个体户，个独
     */
    private MakerType makerType;

    /**
     * 个体户/个独名称
     */
    private String individualName;

    /**
     * 创客到手=服务外包费-创客税费=服务外包费*服务税费率
     */
    private BigDecimal makerNetIncome;

    /**
     * 服务税费率
     */
    private BigDecimal serviceRate;

    /**
     * 服务税费=服务外包费*服务税费率
     */
    private BigDecimal makerTaxFee;

    /**
     * 服务外包费=创客到手/(1-服务税费率)
     */
    private BigDecimal makerNeIncome;

    /**
     * 企业年费总额，个体户，个独，有限公司都有年费，自然人没有年费
     */
    private BigDecimal enterpriseBusinessAnnualFee;

    /**
     * 身份验证费
     */
    private BigDecimal auditFee;

    /**
     * 第三方支付手续费
     */
    private BigDecimal payFee;

    /**
     * 企业总支付额价税合计=服务外包费+身份验证费/个体户年费+第三方支付手续费
     */
    private BigDecimal totalFee;

    /**
     * 支付状态
     */
    private PayMakerPayState payState;

    /**
     * 完税证明开票状态:1:已开；0：未开
     */
    private InvoiceState makerTaxState;

    /**
     * 发票开票状态:1:已开；0：未开
     */
    private InvoiceState makerInvoiceState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
