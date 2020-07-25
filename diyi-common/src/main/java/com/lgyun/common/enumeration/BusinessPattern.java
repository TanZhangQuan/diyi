package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务外包模式
 */
@Getter
@AllArgsConstructor
public enum BusinessPattern {
    NATCROSOUCOM("NATCROSOUCOM", "自然人众包（普票）"),
    NATGENSUBSOUSPE("NATGENSUBSOUSPE", "自然人总包+分包（专票）"),
    SELFCROSOUSPE("SELFCROSOUSPE", "个体户众包（专票）"),
    SELFGENSUBSOUSPE("SELFGENSUBSOUSPE", "个体户总包+分包（专票）"),
    SELFCROSOUCOM("SELFCROSOUCOM", "个体户众包（普票）");

    private final String value;
    private final String desc;

}