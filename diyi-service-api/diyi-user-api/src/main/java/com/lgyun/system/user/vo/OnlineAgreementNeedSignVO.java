package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.TemplateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class OnlineAgreementNeedSignVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "需要签署的授权协议ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long onlineAgreementNeedSignId;

    @ApiModelProperty(value = "平台在线协议模板ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long onlineAgreementTemplateId;

    @ApiModelProperty(value = "合同协议类别")
    private AgreementType agreementType;

    @ApiModelProperty(value = "平台在线协议模板")
    private String agreementTemplate;

    @ApiModelProperty(value = "签署文件模板类型")
    private TemplateType templateType;

    @ApiModelProperty(value = "签署状态")
    private SignState signState;

    @ApiModelProperty(value = "在线合同协议")
    private String agreementUrl;
}
