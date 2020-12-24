package com.lgyun.auth.dto;

import com.lgyun.common.enumeration.UserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel(description = "修改密码DTO")
public class UpdatePasswordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户类型", notes = "com.lgyun.common.enumeration.UserType")
    @NotNull(message = "请选择用户类型")
    private UserType userType;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "请输入手机号")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "短信验证码")
    @NotBlank(message = "请输入短信验证码")
    private String smsCode;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "请输入新密码")
    @Length(min = 6, max = 18, message = "请输入长度为6-18位新密码")
    private String newPassword;

}
