package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 创客商户关系
 */
@Getter
@AllArgsConstructor
public enum RelationshipType {
    RELEVANCE("RELEVANCE", "关联"),
    ATTENTION("ATTENTION", "关注"),
    NORELATION("NORELATION", "不关联不关注");

    private final String value;
    private final String desc;

}