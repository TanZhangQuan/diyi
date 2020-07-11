package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 个体工商户状态
 */
@Getter
@AllArgsConstructor
public enum Ibstate {
    REGISTERING("REGISTERING", "注册中"),
    TAXREGISTERING("TAXREGISTERING", "税务登记中"),
    OPERATING("OPERATING", "运营中"),
    CANCELLED("CANCELLED", "已注销");

    private final String value;
    private final String desc;

}