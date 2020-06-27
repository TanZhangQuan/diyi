package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 岗位性质
 */
@Getter
public enum PositionName {
    marketing("marketing", "营销人员"),
    service("service", "客服人员"),
    operation("operation", "运营人员"),
    management("management", "管理人员"),
    others("others", "其他");

    private String value;
    private String desc;

    PositionName(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}