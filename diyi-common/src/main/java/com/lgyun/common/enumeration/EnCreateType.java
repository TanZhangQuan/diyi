package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 创建类型
 */
@Getter
public enum EnCreateType {
    PLATFORMCREATE("PLATFORMCREATE", "平台创建"),
    SELFREGISTER("SELFREGISTER", "自注册");

    private String value;
    private String desc;

    EnCreateType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}