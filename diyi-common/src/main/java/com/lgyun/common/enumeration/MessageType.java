package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum MessageType {
    CODE("CODE", "验证码"),
    FACEOCRLINK("FACEOCRLINK", "活体认证");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}