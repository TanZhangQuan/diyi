package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "商户基本信息")
public class EnterpriseInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "法人名称")
    private String legalPersonName;

    @ApiModelProperty(value = "法人身份证")
    private String legalPersonIdcard;

    @ApiModelProperty(value = "统一社会信用代码")
    private String socialCreditNo;

    @ApiModelProperty(value = "营业执照")
    private String bizLicenceUrl;

    @ApiModelProperty(value = "企业网址")
    private String enterpriseUrl;
}
