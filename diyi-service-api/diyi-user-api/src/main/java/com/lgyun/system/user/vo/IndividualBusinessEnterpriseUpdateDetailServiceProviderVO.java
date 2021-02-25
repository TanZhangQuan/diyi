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
public class IndividualBusinessEnterpriseUpdateDetailServiceProviderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "个体户/个独ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "税种")
    private BizType bizType;

    @ApiModelProperty(value = "个体户/个独名称")
    private String ibname;

    @ApiModelProperty(value = "统一社会信用代码")
    private String ibtaxNo;

    @ApiModelProperty(value = "营业执照的注册日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date buildDateTime;

    @ApiModelProperty(value = "园区")
    private String bizPark;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "市")
    private String city;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "XXXXXX")
    private BigDecimal registeredMoney;

    @ApiModelProperty(value = "经营场所")
    private String businessAddress;

    @ApiModelProperty(value = "主要行业")
    private String mainIndustry;

    @ApiModelProperty(value = "经营范围")
    private String bizScope;

    @ApiModelProperty(value = "注册时候选名称")
    private String candidatedNames;

    @ApiModelProperty(value = "网络经营场所")
    private String netBusinessAddress;

    @ApiModelProperty(value = "营业执照正本")
    private String businessLicenceUrl;

    @ApiModelProperty(value = "营业执照副本")
    private String businessLicenceCopyUrl;

    @ApiModelProperty(value = "个体户/个独状态")
    private Ibstate ibstate;

    @ApiModelProperty(value = "提交日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date submitDateTime;

    @ApiModelProperty(value = "注册日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registeredDate;

    @ApiModelProperty(value = "税务登记日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date taxRegisterDateTime;

    @ApiModelProperty(value = "联系人姓名")
    private String contactName;

    @ApiModelProperty(value = "联系人手机号")
    private String contactPhone;

    @ApiModelProperty(value = "服务费率")
    private BigDecimal serviceRate;

}
