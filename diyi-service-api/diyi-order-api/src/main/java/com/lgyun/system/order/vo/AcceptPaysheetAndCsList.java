package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
public class AcceptPaysheetAndCsList implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总包+分包/众包交付支付验收单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 支付总额
     */
    private BigDecimal payAmount;

    /**
     * 服务开始日期
     */
    @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
    private Date serviceTimeStart;

    /**
     * 服务结束日期
     */
    @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
    private Date serviceTimeEnd;

}
