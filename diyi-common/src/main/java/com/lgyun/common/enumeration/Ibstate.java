package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 个体工商户状态
 */
@Getter
public enum Ibstate {
    registering("registering", "注册中"),
    taxRegistering("taxRegistering", "税务登记中"),
    operating("operating", "运营中"),
    cancelled("cancelled", "已注销");

    private String value;
    private String desc;

    Ibstate(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}