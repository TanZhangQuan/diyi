package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 签署类型
 */
@Getter
public enum SignType {
    PAPERAGREEMENT("PAPERAGREEMENT", "纸质协议"),
    PLATFORMAGREEMENT("PLATFORMAGREEMENT", "平台协议"),
    THIRDAGREEMENT("THIRDAGREEMENT", "第三方协议");

    private String value;
    private String desc;

    SignType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}