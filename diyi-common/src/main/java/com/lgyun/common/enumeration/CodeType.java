package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证码类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum CodeType {
    LOGIN("LOGIN", "登录验证码"),
    REGISTER("REGISTER", "注册验证码"),
    UPDATEPASSWORD("UPDATEPASSWORD", "修改密码验证码"),
    UPDATEPHONE("UPDATEPHONE", "修改手机号码验证码");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}