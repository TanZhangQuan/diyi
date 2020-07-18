package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交付支付验收单类型
 */
@Getter
@AllArgsConstructor
public enum AcceptPaysheetType {
    MANY("MANY", "清单式"),
    SINGLE("SINGLE", "单人单张");

    private final String value;
    private final String desc;

}