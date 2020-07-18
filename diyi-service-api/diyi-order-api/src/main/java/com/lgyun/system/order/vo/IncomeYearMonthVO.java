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
@ApiModel(value = "IncomeYearMonthVO对象", description = "IncomeYearMonthVO对象")
public class IncomeYearMonthVO implements Serializable {

    //输入记录数
    private Integer num;

    //总收入
    private BigDecimal income;

}
