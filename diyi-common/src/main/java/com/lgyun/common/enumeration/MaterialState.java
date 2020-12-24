package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档属性
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum MaterialState {
    USEING("USEING", "应用中"),
    REMOVED("REMOVED", "已下架");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}