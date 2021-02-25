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
public enum OpenAtribute {
    GLOBALPUBLIC("GLOBALPUBLIC", "全局公开"),
    MANAGEMENTCENTER("MANAGEMENTCENTER", "仅对管理中心公开");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}