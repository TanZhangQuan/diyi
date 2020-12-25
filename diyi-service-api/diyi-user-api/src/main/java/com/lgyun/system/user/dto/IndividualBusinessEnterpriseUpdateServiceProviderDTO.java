package com.lgyun.system.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.BizType;
import com.lgyun.common.enumeration.Ibstate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class IndividualBusinessEnterpriseUpdateServiceProviderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "个体户/个独ID")
    @NotNull(message = "请选择个体户/个独")
    private Long individualId;

    @ApiModelProperty(value = "核定税种", notes = "com.lgyun.common.enumeration.BizType")
    @NotNull(message = "请选择核定税种")
    private BizType bizType;

    @ApiModelProperty(value = "个体户名称")
    private String ibname;

    @ApiModelProperty(value = "统一社会信用代码")
    private String ibtaxNo;

    @ApiModelProperty(value = "营业执照的注册日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date buildDateTime;

    @ApiModelProperty(value = "园区")
    private String bizPark;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "注册资金")
    @NotNull(message = "请输入注册资金")
    @Min(value = 0, message = "注册资金不能小于0")
    private BigDecimal registeredMoney;

    @ApiModelProperty(value = "经营场所")
    private String businessAddress;

    @ApiModelProperty(value = "主要行业")
    @NotBlank(message = "请选择行业类型")
    private String mainIndustry;

    @ApiModelProperty(value = "经营范围")
    @NotBlank(message = "请选择经营范围")
    private String bizScope;

    @ApiModelProperty(value = "注册时候选名称")
    @NotBlank(message = "请输入注册候选名称")
    private String candidatedNames;

    @ApiModelProperty(value = "网络经营场所")
    private String netBusinessAddress;

    @ApiModelProperty(value = "营业执照正本")
    private String businessLicenceUrl;

    @ApiModelProperty(value = "营业执照副本")
    private String businessLicenceCopyUrl;

    @ApiModelProperty(value = "个体户/个独状态", notes = "com.lgyun.common.enumeration.Ibstate")
    @NotNull(message = "请选择状态")
    private Ibstate ibstate;

    @ApiModelProperty(value = "提交日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitDateTime;

    @ApiModelProperty(value = "注册日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date registeredDate;

    @ApiModelProperty(value = "税务登记日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taxRegisterDateTime;

    @ApiModelProperty(value = "联系人姓名")
    @NotBlank(message = "请输入联系人名称")
    private String contactName;

    @ApiModelProperty(value = "联系人手机号")
    @NotBlank(message = "请输入联系人手机号")
    private String contactPhone;

    @ApiModelProperty(value = "服务费率")
    private BigDecimal serviceRate;

}
