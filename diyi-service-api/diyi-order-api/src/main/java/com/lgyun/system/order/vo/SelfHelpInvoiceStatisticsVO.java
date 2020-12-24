package com.lgyun.system.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class SelfHelpInvoiceStatisticsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开票数")
    private Integer num;

    @ApiModelProperty(value = "月度开票金额")
    private BigDecimal monthMoney;

    @ApiModelProperty(value = "年度开票金额")
    private BigDecimal yearMoney;

    @ApiModelProperty(value = "开票总金额")
    private BigDecimal allMoney;

}
