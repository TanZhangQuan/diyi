package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申报主题
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum ReportTheme {
    INDUSTRIALYEARDECLARE("INDUSTRIALYEARDECLARE", "工商年度申报"),
    QUARTERTAX("QUARTERTAX", "季度报税"),
    YEARTAX("YEARTAX", "年度报税"),
    PARKYEARREPORT("PARKYEARREPORT", "园区年报"),
    OTHERREPORT("OTHERREPORT", "其他申报");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}