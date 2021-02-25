package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设置性质
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum SetType {
    SYSTEM("SYSTEM", "系统默认设置"),
    MANUAL("MANUAL", "手工设置");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}