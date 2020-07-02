package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 业务外包模式
 */
@Getter
public enum OrderBusinessPattern {
    SELFGENSUBSOU("NATCROSOUCOM", "个体户-总包+分包"),
    SELFCROSOU("NATGENSUBSOUSPE", "个体户-众包"),
    NATGENSUBSOU("SELCROSOUSPE", "自然人-总包+分包"),
    NATCROSOU("SELGENSUBSOUSPE", "自然人-众包");

    private String value;
    private String desc;

    OrderBusinessPattern(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}