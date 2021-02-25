package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CertificateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "服务商列表VO")
public class ServiceProviderCertUpdateDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商资格信息ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "类别")
    private CertificateType certificateType;

    @ApiModelProperty(value = "资格名称")
    private String certificateName;

    @ApiModelProperty(value = "资格说明")
    private String certificateDesc;

    @ApiModelProperty(value = "资格证书正本")
    private String certificateMainUrl;

}
