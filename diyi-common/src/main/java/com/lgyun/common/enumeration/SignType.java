package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 签署类型
 */
@Getter
@AllArgsConstructor
public enum SignType {
    PAPERAGREEMENT("PAPERAGREEMENT", "纸质协议"),
    PLATFORMAGREEMENT("PLATFORMAGREEMENT", "平台协议"),
    THIRDAGREEMENT("THIRDAGREEMENT", "第三方协议");

    private final String value;
    private final String desc;

}