package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 时间类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum TimeType {
    ALL("ALL", "全部"),
    YEAR("YEAR", "年"),
    MONTH("MONTH", "月"),
    WEEK("WEEK", "周"),
    DAY("DAY", "日"),
    PERIOD("PERIOD", "时间段");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}