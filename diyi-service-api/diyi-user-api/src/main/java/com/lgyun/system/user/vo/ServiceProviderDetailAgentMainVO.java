package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class ServiceProviderDetailAgentMainVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "法人名称")
    private String legalPersonName;

    @ApiModelProperty(value = "联系人")
    private String contact1Name;

    @ApiModelProperty(value = "联系人电话")
    private String contact1Phone;

    @ApiModelProperty(value = "统一社会信用代码")
    private String socialCreditNo;

    @ApiModelProperty(value = "营业执照")
    private String bizLicenceUrl;

    @ApiModelProperty(value = "加盟合同")
    private String joinContract;

    @ApiModelProperty(value = "状态")
    private AccountState serviceProviderState;

    @ApiModelProperty(value = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
