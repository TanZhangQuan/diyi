package com.lgyun.system.order.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tzq.
 * @date 2020/6/29.
 * @time 20:04.
 */
@Data
@ApiModel(value = "IncomeByYearMonthVO对象", description = "IncomeByYearMonthVO对象")
public class IncomeYearMonthVO {

    //发布时间
    private Date publishDate;

    //总收入
    private BigDecimal income;

}
