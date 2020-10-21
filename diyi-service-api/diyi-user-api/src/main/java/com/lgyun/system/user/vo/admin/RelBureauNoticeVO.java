package com.lgyun.system.user.vo.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "通知消息")
public class RelBureauNoticeVO {

    @ApiModelProperty("通知标题")
    private String noticeTitle;

    @ApiModelProperty("通知摘要")
    private String noticeDesc;

    @ApiModelProperty("通知的发布时间")
    private LocalDateTime publishDatetime;

    @ApiModelProperty("通知的发布联系人")
    private String contactPerson;
}
