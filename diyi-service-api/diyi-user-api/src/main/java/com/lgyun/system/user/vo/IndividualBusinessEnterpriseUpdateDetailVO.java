package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.BizType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXXX")
public class IndividualBusinessEnterpriseUpdateDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "个体户/个独ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    @ApiModelProperty(value = "注册时候选名称")
    private String candidatedNames;

    @ApiModelProperty(value = "主要行业")
    private String mainIndustry;

    @ApiModelProperty(value = "经营范围")
    private String bizScope;

    @ApiModelProperty(value = "税种")
    private BizType bizType;

    @ApiModelProperty(value = "注册资金")
    private BigDecimal registeredMoney;

    @ApiModelProperty(value = "联系人姓名")
    private String contactName;

    @ApiModelProperty(value = "联系人手机号")
    private String contactPhone;

    @ApiModelProperty(value = "创客姓名")
    private String name;

    @ApiModelProperty(value = "创客手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "创客身份证号码")
    private String idcardNo;

    @ApiModelProperty(value = "创客身份证正面图")
    private String idcardPic;

    @ApiModelProperty(value = "创客身份证反面图")
    private String idcardPicBack;

    @ApiModelProperty(value = "创客手持证件正面照")
    private String idcardHand;

    @ApiModelProperty(value = "创客手持证件反面照")
    private String idcardBackHand;

}
