package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RelBureauNoticeFileListServiceProviderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 通知/监督文件ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 通知/监督文件标题
     */
    private String noticeFileTitle;

    /**
     * 通知/监督文件摘要
     */
    private String noticeFileDesc;

    /**
     * 通知/监督文件阅读状态
     */
    private Boolean boolRead;

    /**
     * 发布日期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishDatetime;

}
