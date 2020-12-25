package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class RelBureauFileUpdateDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "监督文件ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "监督文件标题")
    private String fileTitle;

    @ApiModelProperty(value = "监督文件摘要")
    private String fileDesc;

    @ApiModelProperty(value = "监督文件文件")
    private String fileUrl;

    @ApiModelProperty(value = "发布联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系人手机")
    private String contactPhone;

    @ApiModelProperty(value = "联系人微信")
    private String contactWechat;

}
