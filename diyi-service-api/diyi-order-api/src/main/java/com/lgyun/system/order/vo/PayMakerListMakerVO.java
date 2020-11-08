package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
     * 创客身份
     */
    private MakerType makerType;

    /**
     * 到手服务费
     */
    private BigDecimal makerNetIncome;

    /**
     * 综合税+费
     */
    private BigDecimal makerTaxFee;

    /**
     * 首次身份验证费
     */
    private BigDecimal auditFee;

    /**
     * 第三方支付手续费
     */
    private BigDecimal payFee;

    /**
     * 企业总支付金额
     */
    private BigDecimal totalFee;

    /**
     * 综合税费率
     */
    private BigDecimal serviceRate;

    /**
     * 支付状态
     */
    private PayMakerPayState payState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
