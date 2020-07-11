package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 个体户税种
 */
@Getter
@AllArgsConstructor
public enum IndBusTaxType {
    SMALL("SMALL", "小规模");

    private final String value;
    private final String desc;

}