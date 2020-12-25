package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class MakerEnterpriseDetailYearMonthVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收入")
    private BigDecimal income;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "验收金额")
    private BigDecimal checkMoney;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "GMT+8")
    private Date checkDate;

}
