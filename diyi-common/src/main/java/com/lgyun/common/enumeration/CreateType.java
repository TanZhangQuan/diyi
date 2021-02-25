package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 创建类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum CreateType {
    PLATFORMCREATE("PLATFORMCREATE", "平台创建"),
    SELFREGISTER("SELFREGISTER", "自注册");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}