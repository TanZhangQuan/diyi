package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 时间类型
 */
@Getter
@AllArgsConstructor
public enum TimeType {
    ALL("ALL", "全部"),
    YEAR("YEAR", "年"),
    MONTH("MONTH", "月"),
    WEEK("WEEK", "周"),
    DAY("DAY", "日"),
    PERIOD("PERIOD", "时间段");

    private final String value;
    private final String desc;

}