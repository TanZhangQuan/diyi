package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.BizType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "添加个体户DTO")
public class IndividualBusinessEnterpriseAddMakerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "注册资金")
    @NotNull(message = "请输入注册资金")
    private BigDecimal registeredMoney;

    @ApiModelProperty(value = "主要行业")
    @NotBlank(message = "请选择主要行业")
    private String mainIndustry;

    @ApiModelProperty(value = "经营范围")
    @NotBlank(message = "请输入经营范围")
    private String bizScope;

    @ApiModelProperty(value = "税种", notes = "com.lgyun.common.enumeration.BizType")
    @NotNull(message = "请选择税种")
    private BizType bizType;

    @ApiModelProperty(value = "注册时候选名称")
    @NotBlank(message = "请输入企业名称")
    private String candidatedNames;

    @ApiModelProperty(value = "联系人姓名")
    @NotBlank(message = "请输入联系人姓名")
    private String contactName;

    @ApiModelProperty(value = "联系人手机号")
    @NotBlank(message = "请输入联系人手机号")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String contactPhone;

}
