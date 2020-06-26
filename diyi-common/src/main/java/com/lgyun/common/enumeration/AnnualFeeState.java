package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 年费状态
 */
@Getter
public enum AnnualFeeState {
    toPayCost("toPayCost", "待缴费"),
    YetPayCost("YetPayCost", "已缴费");

    private String value;
    private String desc;

    AnnualFeeState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}