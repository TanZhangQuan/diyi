package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 性别
 */
@Getter
public enum Gender {
    UNKNOW("UNKNOW", "未知"),
    MALE("MALE", "男"),
    FEMALE("FEMALE", "女");

    private String value;
    private String desc;

    Gender(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}