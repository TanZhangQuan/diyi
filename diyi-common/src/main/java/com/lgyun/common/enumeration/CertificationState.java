package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证状态
 */
@Getter
@AllArgsConstructor
public enum CertificationState {
    UNCERTIFIED("UNCERTIFIED", "未认证"),
    CERTIFIED("CERTIFIED", "已认证");

    private final String value;
    private final String desc;

}