package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 岗位性质
 */
@Getter
public enum PositionName {
    MARKETING("MARKETING", "营销人员"),
    SERVICE("SERVICE", "客服人员"),
    OPERATION("OPERATION", "运营人员"),
    MANAGEMENT("MANAGEMENT", "管理人员"),
    OTHERS("OTHERS", "其他");

    private String value;
    private String desc;

    PositionName(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}