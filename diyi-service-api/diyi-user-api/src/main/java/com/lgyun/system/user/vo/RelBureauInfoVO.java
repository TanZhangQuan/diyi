package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class RelBureauInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 相关局ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 用户名
     */
    private String relBureauUserName;

    /**
     * 相关局名称
     */
    private String relBureauName;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 网址
     */
    private String relBureauWebsite;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人职位
     */
    private String contactPosition;

    /**
     * 联系人电话手机（必填）
     */
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    private String contactMail;

    /**
     * 联系人微信
     */
    private String contactWechat;

    /**
     * 局长姓名
     */
    private String directorName;

    /**
     * 局长联系电话
     */
    private String directorPhone;

    /**
     * 副局长姓名
     */
    private String viceDirectorName;

    /**
     * 副局长联系电话
     */
    private String viceDirectorPhone;

}
