package com.lgyun.system.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "编辑相关局")
public class UpdateRelBureauDTO {

    /**
     * 税务局Id
     */
    @NotNull(message = "相关局Id不能为空！")
    private Long bureauId;

    /**
     * 税务局名称
     */
    @NotBlank(message = "名称不能为空！")
    private String relBureauName;

    /**
     * 地址
     */
    private String relBureauAddress;

    /**
     * 网址
     */
    private String relBureauWebsite;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系人职位
     */
    private String contactPosition;

    /**
     * 联系电话
     */
    private String telPhoneNo;

    /**
     * 联系手机
     */
    @NotBlank(message = "请输入联系人1电话/手机")
    @Length(min = 11, max = 11, message = "请输入11位的联系人1电话/手机")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的联系人1电话/手机")
    private String mobileNo;

    /**
     * 联系微信
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
    @NotBlank(message = "用户名不能为空！")
    private String relBuserName;

    /**
     * 密码
     */
    private String passWord;

}
