package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 签署状态
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum SignState {
    UNSIGN("UNSIGN", "未签署"),
    SIGNED("SIGNED", "已签署");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}

