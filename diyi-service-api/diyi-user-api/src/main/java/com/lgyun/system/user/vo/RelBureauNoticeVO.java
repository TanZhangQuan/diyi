package com.lgyun.system.user.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "通知消息")
public class RelBureauNoticeVO {

    /**
     * 通知标题
     */
    private String noticeTitle;

    /**
     * 通知摘要
     */
    private String noticeDesc;

    /**
     * 通知的发布时间
     */
    private LocalDateTime publishDatetime;

    /**
     * 通知的发布联系人
     */
    private String contactPerson;
}
