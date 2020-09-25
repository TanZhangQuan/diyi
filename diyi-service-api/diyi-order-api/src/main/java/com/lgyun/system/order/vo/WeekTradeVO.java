package com.lgyun.system.order.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
@ApiModel(value = "WeekTradeVO对象", description = "WeekTradeVO对象")
public class WeekTradeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 周一收入
     */
    private BigDecimal monIncome;

    /**
     * 周二收入
     */
    private BigDecimal tueIncome;

    /**
     * 周三收入
     */
    private BigDecimal wedIncome;

    /**
     * 周四收入
     */
    private BigDecimal thuIncome;

    /**
     * 周五收入
     */
    private BigDecimal friIncome;

    /**
     * 周六收入
     */
    private BigDecimal satIncome;

    /**
     * 周七收入
     */
    private BigDecimal sunIncome;

}
