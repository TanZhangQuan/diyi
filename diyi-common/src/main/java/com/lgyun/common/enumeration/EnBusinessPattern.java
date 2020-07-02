package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 业务外包模式
 */
@Getter
public enum EnBusinessPattern {
    NATCROSOUCOM("NATCROSOUCOM", "自然人众包（普票）"),
    NATGENSUBSOUSPE("NATGENSUBSOUSPE", "自然人总包+分包（专票）"),
    SELFCROSOUSPE("SELFCROSOUSPE", "个体户众包（专票）"),
    SELFGENSUBSOUSPE("SELFGENSUBSOUSPE", "个体户总包+分包（专票）"),
    SELFCROSOUCOM("SELFCROSOUCOM", "个体户众包（普票）");

    private String value;
    private String desc;

    EnBusinessPattern(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}