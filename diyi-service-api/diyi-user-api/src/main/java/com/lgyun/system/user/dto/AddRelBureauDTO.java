package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.BureauType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "添加相关局")
public class AddRelBureauDTO {
    /**
     * 局类型，TAXBUREAU，税务局；MARSUPANDADM，市场监督管理局；INDUSTRIALPARKS，产业园区；PAYINGAGENCY，支付机构
     */
    @ApiModelProperty("局类型")
    @NotNull(message = "相关局类型不能为空！")
    private BureauType bureauType;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空！")
    private String relBuserName;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @NotBlank(message = "登录密码不能为空！")
    @Length(min = 6, max = 18, message = "请输入6-18位的密码！")
    private String relBpwd;

    /**
     * 税务局名称
     */
    @ApiModelProperty("相关局名称")
    @NotBlank(message = "名称不能为空！")
    private String relBureauName;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    private String relBureauAddress;

    /**
     * 网址
     */
    @ApiModelProperty("网址")
    private String relBureauWebsite;

    /**
     * 联系人
     */
    @ApiModelProperty("联系人")
    private String contactPerson;

    /**
     * 联系人职位
     */
    @ApiModelProperty("联系人职位")
    private String contactPosition;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String telPhoneNo;

    /**
     * 联系手机
     */
    @ApiModelProperty("联系手机")
    @NotBlank(message = "请输入联系人1电话/手机")
    @Length(min = 11, max = 11, message = "请输入11位的联系人1电话/手机")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的联系人1电话/手机")
    private String mobileNo;

    /**
     * 联系微信
     */
    @ApiModelProperty("联系微信")
    private String wechatNo;

    /**
     * 局长姓名
     */
    @ApiModelProperty("局长姓名")
    private String directorName;

    /**
     * 局长联系电话
     */
    @ApiModelProperty("局长联系电话")
    private String directorPhone;

    /**
     * 副局长姓名
     */
    @ApiModelProperty("用户名")
    private String viceDirectorName;

    /**
     * 副局长联系电话
     */
    @ApiModelProperty("副局长联系电话")
    private String viceDirectorPhone;
}
