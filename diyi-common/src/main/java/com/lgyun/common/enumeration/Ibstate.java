package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 个体户/个独状态
 */
@Getter
@AllArgsConstructor
public enum Ibstate {
    EDITING("EDITING", "编辑中"),
    BUSINESSREGISTERING("BUSINESSREGISTERING", "工商注册中"),
    BANKACCOUNTING("BANKACCOUNTING", "银行开户中"),
    TAXREGISTERING("TAXREGISTERING", "税务登记中"),
    OPERATING("OPERATING", "运营中"),
    CANCELLED("CANCELLED", "已注销");

    private final String value;
    private final String desc;

}