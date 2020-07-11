package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 平台在线协议状态
 */
@Getter
@AllArgsConstructor
public enum AgreementState {
    SIGNING("SIGNING", "签署中"),
    SIGNED("SIGNED", "已签署");

    private final String value;
    private final String desc;

}