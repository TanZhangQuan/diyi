package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 身份证验证类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum IdcardVerifyType {
    SYSTEMVERIFY("SYSTEMVERIFY", "系统验证"),
    MANUALVERIFY("MANUALVERIFY", "手工验证");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}