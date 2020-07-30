package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 税种
 */
@Getter
@AllArgsConstructor
public enum BizType {
    SMALL("SMALL", "小规模"),
    TAXPAYER("TAXPAYER", "一般纳税人");

    private final String value;
    private final String desc;

}