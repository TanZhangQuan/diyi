package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证码类型
 */
@Getter
@AllArgsConstructor
public enum CodeType {
    LOGIN("LOGIN", "登陆验证码"),
    REGISTER("REGISTER", "注册验证码"),
    UPDATEPASSWORD("UPDATEPASSWORD", "修改密码验证码");

    private final String value;
    private final String desc;

}