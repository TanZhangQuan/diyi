package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 平台在线协议状态
 */
@Getter
public enum AgreementState {
    SIGNING("SIGNING", "签署中"),
    SIGNED("SIGNED", "已签署");

    private String value;
    private String desc;

    AgreementState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}