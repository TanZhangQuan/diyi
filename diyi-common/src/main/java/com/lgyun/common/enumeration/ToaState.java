package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 三方在线协议状态
 */
@Getter
public enum ToaState {
    signing("signing", "签署中"),
    signed("signed", "已签署");

    private String value;
    private String desc;

    ToaState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}