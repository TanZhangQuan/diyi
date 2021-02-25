package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 签署文件模板类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum TemplateType {
    CONTRACT("CONTRACT", "合同"),
    AUTHORIZATION("AUTHORIZATION", "授权");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}