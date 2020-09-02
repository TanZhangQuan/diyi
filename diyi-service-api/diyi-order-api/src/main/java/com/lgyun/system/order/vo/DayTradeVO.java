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
@ApiModel(value = "DayTradeVO对象", description = "DayTradeVO对象")
public class DayTradeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //一点钟收入
    private BigDecimal oneHourIncome;

    //二点钟收入
    private BigDecimal twoHourIncome;

    //三点钟收入
    private BigDecimal threeHourIncome;

    //四点钟收入
    private BigDecimal fourHourIncome;

    //五点钟收入
    private BigDecimal fiveHourIncome;

    //六点钟收入
    private BigDecimal sixHourIncome;

    //七点钟收入
    private BigDecimal sevenHourIncome;

    //八点钟收入
    private BigDecimal eightHourIncome;

    //九点钟收入
    private BigDecimal nineHourIncome;

    //十点钟收入
    private BigDecimal tenHourIncome;

    //十一点钟收入
    private BigDecimal elevenHourIncome;

    //十二点钟收入
    private BigDecimal twelveHourIncome;

    //十三点钟收入
    private BigDecimal thirteenHourIncome;

    //十四点钟收入
    private BigDecimal fourteenHourIncome;

    //十五点钟收入
    private BigDecimal fifteenHourIncome;

    //十六点钟收入
    private BigDecimal sixteenHourIncome;

    //十七点钟收入
    private BigDecimal seventeenHourIncome;

    //十八点钟收入
    private BigDecimal eighteenHourIncome;

    //十九点钟收入
    private BigDecimal nineteenHourIncome;

    //二十点钟收入
    private BigDecimal twentyHourIncome;

    //二十一点钟收入
    private BigDecimal twentyOneHourIncome;

    //二十二点钟收入
    private BigDecimal twentyTwoHourIncome;

    //二十三点钟收入
    private BigDecimal twentyThreeHourIncome;

    //二十四点钟收入
    private BigDecimal twentyFourHourIncome;

}
