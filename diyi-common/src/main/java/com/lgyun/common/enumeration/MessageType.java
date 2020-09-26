package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信类型
 */
@Getter
@AllArgsConstructor
public enum MessageType {
    CODE("CODE", "验证码"),
    FACEOCRLINK("FACEOCRLINK", "活体认证");

    private final String value;
    private final String desc;

}