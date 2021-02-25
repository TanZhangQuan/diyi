package com.lgyun.system.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXXX")
public class TransactionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "最近30天总包+分包流水")
    private BigDecimal totalSubMonthPay;

    @ApiModelProperty(value = "最近365天总包+分包流水")
    private BigDecimal totalSubYearPay;

    @ApiModelProperty(value = "总包+分包总流水")
    private BigDecimal totalSubAllPay;

    @ApiModelProperty(value = "最近30天众包流水")
    private BigDecimal crowdMonthPay;

    @ApiModelProperty(value = "最近365天众包流水")
    private BigDecimal crowdYearPay;

    @ApiModelProperty(value = "众包总流水")
    private BigDecimal crowdAllPay;

    @ApiModelProperty(value = "总创客数")
    private Integer makerNum;

    @ApiModelProperty(value = "合作商户数")
    private Integer enterpriseNum;

    @ApiModelProperty(value = "合作服务商数")
    private Integer serviceProviderNum;
}
