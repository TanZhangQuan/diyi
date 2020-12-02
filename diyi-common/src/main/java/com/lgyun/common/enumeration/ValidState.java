package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 合同有效性
 */
@Getter
@AllArgsConstructor
public enum ValidState {
    TOVALID("TOVALID", "待生效"),
    VALIDING("VALIDING", "生效中"),
    UNVALID("UNVALID", "已失效");

    private final String value;
    private final String desc;

}