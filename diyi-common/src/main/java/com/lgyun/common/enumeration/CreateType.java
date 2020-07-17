package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 创建类型
 */
@Getter
@AllArgsConstructor
public enum CreateType {
    PLATFORMCREATE("PLATFORMCREATE", "平台创建"),
    SELFREGISTER("SELFREGISTER", "自注册");

    private final String value;
    private final String desc;

}