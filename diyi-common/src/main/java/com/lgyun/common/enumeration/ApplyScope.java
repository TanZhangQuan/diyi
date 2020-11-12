package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 应用范围
 */
@Getter
@AllArgsConstructor
public enum ApplyScope {
    TOTAL("TOTAL", "总包开票范围"),
    CROWD("CROWD", "众包-自然人代开开票范围");

    private final String value;
    private final String desc;

}