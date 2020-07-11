package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别
 */
@Getter
@AllArgsConstructor
public enum Gender {
    UNKNOW("UNKNOW", "未知"),
    MALE("MALE", "男"),
    FEMALE("FEMALE", "女");

    private final String value;
    private final String desc;

}