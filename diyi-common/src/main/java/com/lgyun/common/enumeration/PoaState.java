package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 平台在线协议状态
 */
@Getter
public enum PoaState {
    signing("signing", "签署中"),
    signed("signed", "已签署");

    private String value;
    private String desc;

    PoaState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}