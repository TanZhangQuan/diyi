package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 关单类型
 */
@Getter
@AllArgsConstructor
public enum CloseWorksheetType {
    SYSTEM("SYSTEM", "自动关单"),
    MANUAL("MANUAL", "手动关单");

    private final String value;
    private final String desc;

}