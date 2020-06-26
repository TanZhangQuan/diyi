package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 关联类型
 */
@Getter
public enum RelType {
    makerRel("makerRel", "创客主动关联"),
    enterpriseRel("enterpriseRel", "企业主动关联"),
    platformRel("platformRel", "平台关联");

    private String value;
    private String desc;

    RelType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}