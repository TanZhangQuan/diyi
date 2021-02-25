package com.lgyun.system.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class AddOrUpdateRelBureauFileDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "监督文件ID")
    private Long relBureauFileId;

    @ApiModelProperty(value = "监督文件标题")
    @NotBlank(message = "请输入监督文件标题")
    private String fileTitle;

    @ApiModelProperty(value = "监督文件摘要")
    @NotBlank(message = "请输入监督文件摘要")
    private String fileDesc;

    @ApiModelProperty(value = "监督文件文件")
    @NotBlank(message = "请上传监督文件文件")
    private String fileUrl;

    @ApiModelProperty(value = "发布联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系人手机")
    private String contactPhone;

    @ApiModelProperty(value = "联系人微信")
    private String contactWechat;

}
