package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.SignType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class AgreementServiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "合同ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long agreementId;

    @ApiModelProperty(value = "服务商ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String serviceProviderId;

    @ApiModelProperty(value = "合同编号")
    private String agreementNo;

    @ApiModelProperty(value = "签署类型")
    private SignType signType;

    @ApiModelProperty(value = "协议类别")
    private AgreementType agreementType;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "创客名称")
    private String makerName;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "线下合同地址")
    private String agreementUrl;

    @ApiModelProperty(value = "签署日期")
    private String createTime;

    @ApiModelProperty(value = "签署状态")
    private SignState signState;

}
