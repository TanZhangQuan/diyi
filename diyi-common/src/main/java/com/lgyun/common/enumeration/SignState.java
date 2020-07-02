package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 签署状态
 */
@Getter
public enum SignState {
    UNSIGN("UNSIGN", "无需签署"),
    SIGNING("SIGNING", "签署中"),
    SIGNED("SIGNED", "已签署");

    private String value;
    private String desc;

    SignState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}