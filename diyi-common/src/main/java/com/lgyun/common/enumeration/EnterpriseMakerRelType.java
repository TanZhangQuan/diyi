package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 关联类型
 */
@Getter
@AllArgsConstructor
public enum EnterpriseMakerRelType {
    MAKERREL("MAKERREL", "创客主动关联"),
    ENTERPRISEREL("ENTERPRISEREL", "企业主动关联"),
    PLATFORMREL("PLATFORMREL", "平台关联");

    private final String value;
    private final String desc;

}