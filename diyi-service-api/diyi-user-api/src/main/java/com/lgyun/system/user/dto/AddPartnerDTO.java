package com.lgyun.system.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel(description = "添加合伙人DTO")
public class AddPartnerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "介绍合伙人手机号")
    @Length(min = 11, max = 11, message = "请输入11位的介绍合伙人手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的介绍合伙人手机号")
    private String introducePartnerPhone;

    @ApiModelProperty(value = "合伙人姓名")
    @NotBlank(message = "请输入合伙人姓名")
    private String name;

    @ApiModelProperty(value = "身份证号码")
    @NotBlank(message = "请输入合伙人身份证号码")
    private String idcardNo;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "请输入合伙人手机号码")
    @Length(min = 11, max = 11, message = "请输入11位的合伙人手机号码")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的合伙人手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNo;

    @ApiModelProperty(value = "开户银行")
    private String bankName;

    @ApiModelProperty(value = "开户支行")
    private String subBankName;

    @ApiModelProperty(value = "登陆密码")
    @NotBlank(message = "请输入登陆密码")
    @Length(min = 6, max = 18, message = "请输入长度为6-18位的登陆密码")
    private String loginPwd;
}
