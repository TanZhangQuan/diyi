package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.BizType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXXX")
public class IndividualBusinessEnterpriseDetailMakerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "个体户/个独名称")
    private String ibname;

    @ApiModelProperty(value = "注册时候选名称")
    private String candidatedNames;

    @ApiModelProperty(value = "统一社会信用代码")
    private String ibtaxNo;

    @ApiModelProperty(value = "园区")
    private String bizPark;

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

}
