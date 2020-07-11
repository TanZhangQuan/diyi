package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 签署状态
 */
@Getter
@AllArgsConstructor
public enum SignState {
    UNSIGN("UNSIGN", "无需签署"),
    SIGNING("SIGNING", "签署中"),
    SIGNED("SIGNED", "已签署");

    private final String value;
    private final String desc;

}