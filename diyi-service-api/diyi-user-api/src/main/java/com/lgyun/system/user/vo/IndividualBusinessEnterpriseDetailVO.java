package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.BizType;
import com.lgyun.common.enumeration.Ibstate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class IndividualBusinessEnterpriseDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "个体户/个独ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "个体户/个独名称")
    private String ibname;

    @ApiModelProperty(value = "注册时候选名称")
    private String candidatedNames;

    @ApiModelProperty(value = "统一社会信用代码")
    private String ibtaxNo;

    @ApiModelProperty(value = "主要行业")
    private String mainIndustry;

    @ApiModelProperty(value = "经营范围")
    private String bizScope;

    @ApiModelProperty(value = "税种")
    private BizType bizType;

    @ApiModelProperty(value = "经营者")
    private String bizName;

    @ApiModelProperty(value = "注册资金")
    private BigDecimal registeredMoney;

    @ApiModelProperty(value = "营业执照正本")
    private String businessLicenceUrl;

    @ApiModelProperty(value = "个体户状态")
    private Ibstate ibstate;

    @ApiModelProperty(value = "注册日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registeredDate;

}
