package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 签署状态
 */
@Getter
public enum SignState {
    signing("signing", "签署中"),
    signed("signed", "签署完成");

    private String value;
    private String desc;

    SignState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}