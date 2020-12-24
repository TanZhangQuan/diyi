package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 关单类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum CloseWorksheetType {
    SYSTEM("SYSTEM", "自动关单"),
    MANUAL("MANUAL", "手动关单");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}