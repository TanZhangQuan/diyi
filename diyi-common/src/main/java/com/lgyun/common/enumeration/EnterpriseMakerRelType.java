package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 关联类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum EnterpriseMakerRelType {
    MAKERREL("MAKERREL", "创客主动关联"),
    ENTERPRISEREL("ENTERPRISEREL", "企业主动关联"),
    PLATFORMREL("PLATFORMREL", "平台关联");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}