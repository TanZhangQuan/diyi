package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class RelBureauNoticeUpdateDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "通知ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "通知标题")
    private String noticeTitle;

    @ApiModelProperty(value = "通知摘要")
    private String noticeDesc;

    @ApiModelProperty(value = "通知文件")
    private String noticeUrl;

    @ApiModelProperty(value = "发布联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系人手机")
    private String contactPhone;

    @ApiModelProperty(value = "联系人微信")
    private String contactWechat;

}
