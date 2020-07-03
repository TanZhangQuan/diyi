package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 个独税种
 */
@Getter
public enum IndEntTaxType {
    SMALL("SMALL", "小规模"),
    TAXPAYER("TAXPAYER", "一般纳税人");

    private String value;
    private String desc;

    IndEntTaxType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}