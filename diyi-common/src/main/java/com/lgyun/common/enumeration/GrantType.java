package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 授权类型枚举
 *
 * @author tzq
 * @since 2020/6/6 01:04
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum GrantType {
    PASSWORD("PASSWORD", "账号密码登录"),
    MOBILE("MOBILE", "短信验证码登录"),
    WECHAT("WECHAT", "微信授权登录"),
    REGISTER("REGISTER", "注册"),
    UPDATEPASSWORD("UPDATEPASSWORD", "修改密码");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
