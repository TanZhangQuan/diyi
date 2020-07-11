package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 年费状态
 */
@Getter
@AllArgsConstructor
public enum AnnualFeeState {
    TOPAYCOST("TOPAYCOST", "待缴费"),
    YETPAYCOST("YETPAYCOST", "已缴费");

    private final String value;
    private final String desc;

}