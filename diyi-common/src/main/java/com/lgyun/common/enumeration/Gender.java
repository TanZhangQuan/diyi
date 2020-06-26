package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 性别
 */
@Getter
public enum Gender {
    unknow("unknow", "未知"),
    male("male", "男"),
    female("female", "女");

    private String value;
    private String desc;

    Gender(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}