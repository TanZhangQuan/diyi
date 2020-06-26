package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 签署类型
 */
@Getter
public enum SignType {
    paperAgreement("paperAgreement", "纸质协议"),
    platformAgreement("platformAgreement", "平台协议"),
    thirdAgreement("thirdAgreement", "第三方协议");

    private String value;
    private String desc;

    SignType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}