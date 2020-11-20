package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.RelBureauType;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "添加相关局")
public class AddRelBureauDTO {

    /**
     * 类型，TAXBUREAU，税务局；MARSUPANDADM，市场监督管理局；INDUSTRIALPARKS，产业园区；PAYINGAGENCY，支付机构
     */
    @NotNull(message = "请选择类型")
    private RelBureauType relBureauType;

    /**
     * 用户名
     */
    @NotBlank(message = "请输入用户名")
    private String relBuserName;

    /**
     * 密码
     */
    @NotBlank(message = "请输入密码")
    @Length(min = 6, max = 18, message = "请输入长度为6-18位的密码")
    private String relBpwd;

    /**
     * 税务局名称
     */
    @NotBlank(message = "请输入税务局名称")
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

}
