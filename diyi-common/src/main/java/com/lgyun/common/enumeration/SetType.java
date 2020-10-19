package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设置性质
 */
@Getter
@AllArgsConstructor
public enum SetType {
    SYSTEM("SYSTEM", "系统默认设置"),
    MANUAL("MANUAL", "手工设置");

    private final String value;
    private final String desc;

}