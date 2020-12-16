package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.RelBureauNoticeFileState;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RelBureauNoticeListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 通知ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 通知标题
     */
    private String noticeTitle;

    /**
     * 通知摘要
     */
    private String noticeDesc;

    /**
     * 通知状态
     */
    private RelBureauNoticeFileState noticeState;

    /**
     * 发布日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishDatetime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
