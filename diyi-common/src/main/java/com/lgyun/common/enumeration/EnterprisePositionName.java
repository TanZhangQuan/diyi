package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 岗位性质
 */
@Getter
@AllArgsConstructor
public enum EnterprisePositionName {
    FINANCIAL("FINANCIAL", "财务人员"),
    PERSONNEL("PERSONNEL", "人事人员"),
    OPERATION("OPERATION", "运营人员"),
    MANAGEMENT("MANAGEMENT", "管理人员"),
    OTHERS("OTHERS", "其他");

    private final String value;
    private final String desc;

}