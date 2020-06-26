package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 创客类别
 */
@Getter
public enum MakerType {
    naturalPerson("naturalPerson", "自然人"),
    individualBusiness("individualBusiness", "个体户");

    private String value;
    private String desc;

    MakerType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}