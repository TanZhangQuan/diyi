package com.lgyun.system.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class AllIncomeYearMonthVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收入笔数")
    private Integer num;

    @ApiModelProperty(value = "总收入")
    private BigDecimal income;

}
