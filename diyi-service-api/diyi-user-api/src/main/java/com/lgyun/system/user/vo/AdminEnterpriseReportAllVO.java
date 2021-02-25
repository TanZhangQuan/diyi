package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class AdminEnterpriseReportAllVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "申报ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseReportId;

    @ApiModelProperty(value = "服务商ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    @ApiModelProperty(value = "服务商名字")
    private String serviceProviderName;

    @ApiModelProperty(value = "申请次数")
    private Integer applyCount;
}
