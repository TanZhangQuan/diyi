package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.enumeration.RelBureauType;
import lombok.Data;

import java.io.Serializable;

@Data
public class RelBureauUpdateDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 相关局ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 相关局类型
     */
    private RelBureauType relBureauType;

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
     * 联系人
     */
    private String contactName;

    /**
     * 联系人职位
     */
    private PositionName contactPosition;

    /**
     * 联系手机号
     */
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    private String contactMail;

    /**
     * 联系人手机号
     */
    private String contactWechat;

    /**
     * 联系人微信
     */
    private String wechatNo;

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

    /**
     * 用户名
     */
    private String relBureauUserName;

}
