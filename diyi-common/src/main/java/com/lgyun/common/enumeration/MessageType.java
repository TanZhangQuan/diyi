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
    LINK("LINK", "链接");

    private final String value;
    private final String desc;

}