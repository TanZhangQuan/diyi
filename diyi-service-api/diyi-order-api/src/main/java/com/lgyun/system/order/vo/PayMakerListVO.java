package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tzq
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
@ApiModel(value = "AcceptPayMakerListVO对象", description = "AcceptPayMakerListVO对象")
public class PayMakerListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    private String idcardNo;

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
     * 验收单URL
     */
    private String acceptPaysheetUrl;

}
