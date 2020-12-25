package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.CertificateType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "添加或修改服务商资格信息DTO")
public class AddOrUpdateServiceProviderCertDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商资格信息ID")
    private Long serviceProviderCertId;

    @ApiModelProperty(value = "类别", notes = "com.lgyun.common.enumeration.CertificateType")
    @NotNull(message = "请选择类型")
    private CertificateType certificateType;

    @ApiModelProperty(value = "资格名称")
    @NotBlank(message = "请输入资格名称")
    private String certificateName;

    @ApiModelProperty(value = "资格说明")
    private String certificateDesc;

    @ApiModelProperty(value = "资格证书")
    @NotBlank(message = "请上传资格证书")
    private String certificateMainUrl;

}
