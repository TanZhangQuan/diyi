package com.lgyun.system.order.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
public class AllIncomeYearMonthVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 记录数
     */
    private Integer num;

    /**
     * 总收入
     */
    private BigDecimal income;

}
