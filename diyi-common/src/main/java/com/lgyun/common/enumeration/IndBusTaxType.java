package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 个体户税种
 */
@Getter
public enum IndBusTaxType {
    SMALL("SMALL", "小规模");

    private String value;
    private String desc;

    IndBusTaxType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}