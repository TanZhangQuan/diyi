package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 管理员特性
 */
@Getter
@AllArgsConstructor
public enum AdminPower {
    YES("YSE", "是"),
    NO("NO", "否");

    private final String value;
    private final String desc;
}
