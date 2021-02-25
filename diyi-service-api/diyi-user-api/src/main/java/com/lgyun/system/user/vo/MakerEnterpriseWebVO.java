package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CertificationState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class MakerEnterpriseWebVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "创客-商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerEnterpriseId;

    @ApiModelProperty(value = "创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    @ApiModelProperty(value = "认证状态")
    private CertificationState certificationState;

    @ApiModelProperty(value = "是否有有效的创客授权书")
    private Boolean boolPowerAttorney;

    @ApiModelProperty(value = "是否有有效的创客加盟协议")
    private Boolean boolJoinAgreement;

    @ApiModelProperty(value = "创客名称")
    private String name;
}
