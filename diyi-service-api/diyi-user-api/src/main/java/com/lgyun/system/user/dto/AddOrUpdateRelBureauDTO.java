package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.RelBureauType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class AddOrUpdateRelBureauDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "相关局ID")
    private Long relBureauId;

    @ApiModelProperty(value = "相关局类型", notes = "com.lgyun.common.enumeration.RelBureauType")
    @NotNull(message = "请选择相关局类型")
    private RelBureauType relBureauType;

    @ApiModelProperty(value = "相关局名称")
    @NotBlank(message = "请输入相关局名称")
    private String relBureauName;

    @ApiModelProperty(value = "省")
    @NotBlank(message = "请选择省份")
    private String province;

    @ApiModelProperty(value = "市")
    @NotBlank(message = "请选择市")
    private String city;

    @ApiModelProperty(value = "区")
    @NotBlank(message = "请选择区")
    private String area;

    @ApiModelProperty(value = "详细地址")
    @NotBlank(message = "请输入详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "网址")
    private String relBureauWebsite;

    @ApiModelProperty(value = "联系人")
    @NotBlank(message = "请输入联系人")
    private String contactName;

    @ApiModelProperty(value = "联系人职位")
    @NotBlank(message = "请选择联系人职位")
    private String contactPosition;

    @ApiModelProperty(value = "联系手机号")
    @NotBlank(message = "请输入联系人手机号")
    @Length(min = 11, max = 11, message = "请输入11位的联系人手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的联系人手机号")
    private String contactPhone;

    @ApiModelProperty(value = "联系人邮箱")
    @NotBlank(message = "请输入联系人邮箱")
    @Email(message = "请输入正确的联系人邮箱")
    private String contactMail;

    @ApiModelProperty(value = "联系人手机号")
    @NotBlank(message = "请输入联系人微信")
    private String contactWechat;

    @ApiModelProperty(value = "局长姓名")
    @NotBlank(message = "请输入局长姓名")
    private String directorName;

    @ApiModelProperty(value = "局长联系电话")
    @NotBlank(message = "请输入局长联系电话")
    private String directorPhone;

    @ApiModelProperty(value = "副局长姓名")
    @NotBlank(message = "请输入副局长姓名")
    private String viceDirectorName;

    @ApiModelProperty(value = "副局长联系电话")
    @NotBlank(message = "请输入副局长联系电话")
    private String viceDirectorPhone;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "请输入账号")
    private String relBureauUserName;

    @ApiModelProperty(value = "密码")
    private String relBureauPwd;

}
