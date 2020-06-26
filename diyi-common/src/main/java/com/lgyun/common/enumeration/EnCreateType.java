package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 创建类型
 */
@Getter
public enum EnCreateType {
    platformCreate("platformCreate", "平台创建"),
    selfRegister("selfRegister", "自注册");

    private String value;
    private String desc;

    EnCreateType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}