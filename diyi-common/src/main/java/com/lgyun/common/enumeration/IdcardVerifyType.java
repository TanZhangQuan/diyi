package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 身份证验证类型
 */
@Getter
@AllArgsConstructor
public enum IdcardVerifyType {
    SYSTEMVERIFY("SYSTEMVERIFY", "系统验证"),
    MANUALVERIFY("MANUALVERIFY", "手工验证");

    private final String value;
    private final String desc;

}