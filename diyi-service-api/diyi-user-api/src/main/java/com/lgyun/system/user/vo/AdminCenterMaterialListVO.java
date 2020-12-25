package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.MaterialState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "模板管理VO")
public class AdminCenterMaterialListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "综合业务资料ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "文档名称")
    private String materialName;

    @ApiModelProperty(value = "文件描述")
    private String materialDesc;

    @ApiModelProperty(value = "模板")
    private String materialUrl;

    @ApiModelProperty(value = "状态")
    private MaterialState materialState;

}
