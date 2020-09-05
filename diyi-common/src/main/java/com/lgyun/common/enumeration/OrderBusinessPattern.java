package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务外包模式
 */
@Getter
@AllArgsConstructor
public enum OrderBusinessPattern {
    SELFGENSUBSOU("NATCROSOUCOM", "个体户-总包+分包"),
    SELFCROSOU("NATGENSUBSOUSPE", "个体户-众包/众采"),
    NATGENSUBSOU("SELCROSOUSPE", "自然人-总包+分包"),
    NATCROSOU("SELGENSUBSOUSPE", "自然人-众包/众采");

    private final String value;
    private final String desc;

}