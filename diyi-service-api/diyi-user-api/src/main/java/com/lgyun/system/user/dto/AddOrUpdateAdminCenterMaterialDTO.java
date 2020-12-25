package com.lgyun.system.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "创建综合业务资料DTO")
public class AddOrUpdateAdminCenterMaterialDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "综合业务资料ID")
    private Long adminCenterMaterialId;

    @ApiModelProperty(value = "业务资料名称")
    @NotBlank(message = "请输入文档名称")
    private String materialName;

    @ApiModelProperty(value = "文件URL")
    @NotNull(message = "请上传文件")
    private String materialUrl;

    @ApiModelProperty(value = "文件概述")
    private String materialDesc;

}
