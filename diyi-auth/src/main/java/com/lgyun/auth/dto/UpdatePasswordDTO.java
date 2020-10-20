package com.lgyun.auth.dto;

import com.lgyun.common.enumeration.UserType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author tzq
 * @Description 修改密码DTO
 * @return
 * @date 2020.06.27
 */
@Data
public class UpdatePasswordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户类型
     */
    @NotNull(message = "请选择用户类型")
    private UserType userType;

    /**
     * 手机号
     */
    @NotBlank(message = "请输入手机号")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String phoneNumber;

    /**
     * 短信验证码
     */
    @NotBlank(message = "请输入短信验证码")
    private String smsCode;

    /**
     * 新密码
     */
    @NotBlank(message = "请输入新密码")
    @Length(min = 6, max = 18, message = "请输入长度为6-18位新密码")
    private String newPassword;

}
