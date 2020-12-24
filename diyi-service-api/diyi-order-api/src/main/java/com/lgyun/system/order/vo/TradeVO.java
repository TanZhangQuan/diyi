package com.lgyun.system.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class TradeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "交易时间")
    private String tradeTime;

    @ApiModelProperty(value = "交易流水")
    private BigDecimal tradeMoney;

}
