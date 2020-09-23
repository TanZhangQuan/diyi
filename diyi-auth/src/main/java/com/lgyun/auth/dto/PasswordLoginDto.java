package com.lgyun.auth.dto;

import com.lgyun.common.enumeration.UserType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tzq
 * @Description 账号密码登录DTO
 * @return
 * @date 2020.06.27
 */
@Data
public class PasswordLoginDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户类型
    @NotNull(message = "请选择用户类型")
    private UserType userType;

    //账号
    @NotBlank(message = "请输入账号")
    private String account;

    //密码
    @NotBlank(message = "请输入密码")
    private String password;

    //微信授权码
    private String wechatCode;

}
