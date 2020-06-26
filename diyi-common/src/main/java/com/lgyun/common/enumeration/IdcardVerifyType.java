package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 身份证验证类型
 */
@Getter
public enum IdcardVerifyType {
    systemVerify("systemVerify", "系统验证"),
    manualVerify("manualVerify", "手工验证");

    private String value;
    private String desc;

    IdcardVerifyType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}