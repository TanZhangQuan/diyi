package com.lgyun.system.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddOrUpdateRelBureauNoticeDTO {

    /**
     * 通知ID
     */
    private Long relBureauNoticeId;

    /**
     * 通知标题
     */
    @NotBlank(message = "请输入通知标题")
    private String noticeTitle;

    /**
     * 通知摘要
     */
    @NotBlank(message = "请输入通知摘要")
    private String noticeDesc;

    /**
     * 通知文件
     */
    @NotBlank(message = "请上传通知文件")
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
