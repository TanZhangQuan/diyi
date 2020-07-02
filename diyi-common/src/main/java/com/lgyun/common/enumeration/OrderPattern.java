package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 发单模式
 */
@Getter
public enum OrderPattern {
    OPENGRABORDER("OPENGRABORDER", "公开抢单"),
    UNOPENGRABORDER("UNOPENGRABORDER", "不公开抢单");

    private String value;
    private String desc;

    OrderPattern(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}