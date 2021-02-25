package com.lgyun.system.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "创客关联商户数和收入情况VO")
public class MakerEnterpriseNumIncomeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联商户数")
    private String enterpriseNum;

    @ApiModelProperty(value = "近30天收入")
    private BigDecimal monthIncome;

    @ApiModelProperty(value = "近365天收入")
    private BigDecimal yearIncome;

    @ApiModelProperty(value = "总收入")
    private BigDecimal allIncome;

}
