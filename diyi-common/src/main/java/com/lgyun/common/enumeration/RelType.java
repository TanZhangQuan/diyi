package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 关联类型
 */
@Getter
public enum RelType {
    MAKERREL("MAKERREL", "创客主动关联"),
    ENTERPRISEREL("ENTERPRISEREL", "企业主动关联"),
    PLATFORMREL("PLATFORMREL", "平台关联");

    private String value;
    private String desc;

    RelType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}