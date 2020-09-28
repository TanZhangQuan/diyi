package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
public class IncomeYearVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 年份
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long year;

    /**
     * 收入
     */
    private BigDecimal yearIncome;

}
