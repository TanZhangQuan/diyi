package com.lgyun.system.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class AllIncomeYearMonthEnterpriseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "创客收入")
    private BigDecimal income;

}
