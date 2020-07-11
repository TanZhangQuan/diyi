package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 发单模式
 */
@Getter
@AllArgsConstructor
public enum OrderPattern {
    OPENGRABORDER("OPENGRABORDER", "公开抢单"),
    UNOPENGRABORDER("UNOPENGRABORDER", "不公开抢单");

    private final String value;
    private final String desc;

}