package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 签署状态
 */
@Getter
@AllArgsConstructor
public enum SignState {
    UNSIGN("UNSIGN", "未签署"),
    SIGNED("SIGNED", "已签署");

    private final String value;
    private final String desc;

}

