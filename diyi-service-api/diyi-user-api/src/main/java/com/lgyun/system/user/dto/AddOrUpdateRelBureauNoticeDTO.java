package com.lgyun.system.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class AddOrUpdateRelBureauNoticeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "通知ID")
    private Long relBureauNoticeId;

    @ApiModelProperty(value = "通知标题")
    @NotBlank(message = "请输入通知标题")
    private String noticeTitle;

    @ApiModelProperty(value = "通知摘要")
    @NotBlank(message = "请输入通知摘要")
    private String noticeDesc;

    @ApiModelProperty(value = "通知文件")
    @NotBlank(message = "请上传通知文件")
    private String noticeUrl;

    @ApiModelProperty(value = "发布联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系人手机")
    private String contactPhone;

    @ApiModelProperty(value = "联系人微信")
    private String contactWechat;

}
