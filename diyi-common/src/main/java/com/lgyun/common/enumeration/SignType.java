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
    UNILATERALPOWER("UNILATERALPOWER", "单方授权函(纸)"),
    ELEUNILATERALPOWER("ELEUNILATERALPOWER", "单方授权函(电)"),
    THIRDAGREEMENT("THIRDAGREEMENT", "第三方协议");

    private final String value;
    private final String desc;

}
