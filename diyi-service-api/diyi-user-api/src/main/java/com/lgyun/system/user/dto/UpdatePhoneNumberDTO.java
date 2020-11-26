package com.lgyun.system.user.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class UpdatePhoneNumberDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 手机号
     */
    @NotBlank(message = "请输入手机号")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号")
    private String mobile;

    /**
     * 短信验证码
     */
    @NotBlank(message = "请输入短信验证码")
    private String smsCode;

}
