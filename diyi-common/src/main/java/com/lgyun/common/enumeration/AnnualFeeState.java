package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 年费状态
 */
@Getter
public enum AnnualFeeState {
    TOPAYCOST("TOPAYCOST", "待缴费"),
    YETPAYCOST("YETPAYCOST", "已缴费");

    private String value;
    private String desc;

    AnnualFeeState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}