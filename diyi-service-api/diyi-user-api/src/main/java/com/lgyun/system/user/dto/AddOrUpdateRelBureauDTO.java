package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.PositionName;
import com.lgyun.common.enumeration.RelBureauType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class AddOrUpdateRelBureauDTO {

    /**
     * 相关局ID
     */
    private Long relBureauId;

    /**
     * 相关局类型
     */
    @NotNull(message = "请选择相关局类型")
    private RelBureauType relBureauType;

    /**
     * 相关局名称
     */
    @NotBlank(message = "请输入相关局名称")
    private String relBureauName;

    /**
     * 省
     */
    @NotBlank(message = "请选择省份")
    private String province;

    /**
     * 市
     */
    @NotBlank(message = "请选择市")
    private String city;

    /**
     * 区
     */
    @NotBlank(message = "请选择区")
    private String area;

    /**
     * 详细地址
     */
    @NotBlank(message = "请输入详细地址")
    private String detailedAddress;

    /**
     * 网址
     */
    private String relBureauWebsite;

    /**
     * 联系人
     */
    @NotBlank(message = "请输入联系人")
    private String contactName;

    /**
     * 联系人职位
     */
    @NotBlank(message = "请选择联系人职位")
    private PositionName contactPosition;

    /**
     * 联系手机号
     */
    @NotBlank(message = "请输入联系人手机号")
    @Length(min = 11, max = 11, message = "请输入11位的联系人手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的联系人手机号")
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    @NotBlank(message = "请输入联系人邮箱")
    @Email(message = "请输入正确的联系人邮箱")
    private String contactMail;

    /**
     * 联系人手机号
     */
    @NotBlank(message = "请输入联系人微信")
    private String contactWechat;

    /**
     * 局长姓名
     */
    @NotBlank(message = "请输入局长姓名")
    private String directorName;

    /**
     * 局长联系电话
     */
    @NotBlank(message = "请输入局长联系电话")
    private String directorPhone;

    /**
     * 副局长姓名
     */
    @NotBlank(message = "请输入副局长姓名")
    private String viceDirectorName;

    /**
     * 副局长联系电话
     */
    @NotBlank(message = "请输入副局长联系电话")
    private String viceDirectorPhone;

    /**
     * 用户名
     */
    @NotBlank(message = "请输入账号")
    private String relBureauUserName;

    /**
     * 密码
     */
    private String relBureauPwd;

}
