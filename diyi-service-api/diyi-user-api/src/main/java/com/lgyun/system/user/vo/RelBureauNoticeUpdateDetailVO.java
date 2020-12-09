package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class RelBureauNoticeUpdateDetailVO implements Serializable {
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
     * 通知文件
     */
    private String noticeUrl;

    /**
     * 发布联系人
     */
    private String contactPerson;

    /**
     * 联系人手机
     */
    private String contactPhone;

    /**
     * 联系人微信
     */
    private String contactWechat;

}
