package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.SignState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "XXXXXX")
public class AgreementWebVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户-服务商ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseProviderId;

    @ApiModelProperty(value = "合同ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long agreementId;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "服务商ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "合同编号")
    private String agreementNo;

    @ApiModelProperty(value = "纸质合同")
    private String agreementUrl;

    @ApiModelProperty(value = "合同状态")
    private SignState signState;

    @ApiModelProperty(value = "签署时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
