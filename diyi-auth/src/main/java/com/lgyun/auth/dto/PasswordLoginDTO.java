package com.lgyun.auth.dto;

import com.lgyun.common.enumeration.RelBureauType;
import com.lgyun.common.enumeration.UserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "账号密码登录DTO")
public class PasswordLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户类型", notes = "com.lgyun.common.enumeration.UserType")
    @NotNull(message = "请选择用户类型")
    private UserType userType;

    @ApiModelProperty(value = "相关局类型", notes = "com.lgyun.common.enumeration.RelBureauType")
    private RelBureauType relBureauType;

    @ApiModelProperty(value = "手机号/用户名")
    @NotBlank(message = "请输入手机号/用户名")
    private String account;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "请输入密码")
    @Length(min = 6, max = 18, message = "请输入长度为6-18位的密码")
    private String password;

    @ApiModelProperty(value = "微信授权码")
    private String wechatCode;

}
