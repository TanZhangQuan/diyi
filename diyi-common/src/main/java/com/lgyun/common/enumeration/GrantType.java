package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 授权类型枚举
 *
 * @author liangfeihu
 * @since 2020/6/6 01:04
 */
@Getter
@AllArgsConstructor
public enum GrantType {
    PASSWORD("PASSWORD", "账号密码登陆"),
    MOBILE("MOBILE", "短信验证码登陆"),
    WECHAT("WECHAT", "微信授权登陆"),
    REGISTER("REGISTER", "注册"),
    REFRESHTOKEN("REFRESHTOKEN", "刷新token");

    private final String value;
    private final String desc;

}
