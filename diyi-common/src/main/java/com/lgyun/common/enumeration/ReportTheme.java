package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申报主题
 */
@Getter
@AllArgsConstructor
public enum ReportTheme {
    INDUSTRIALYEARDECLARE("INDUSTRIALYEARDECLARE", "工商年度申报"),
    QUARTERTAX("QUARTERTAX", "季度报税"),
    YEARTAX("YEARTAX", "年度报税"),
    PARKYEARREPORT("PARKYEARREPORT", "园区年报"),
    OTHERREPORT("OTHERREPORT", "其他申报");

    private final String value;
    private final String desc;

}