package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档属性
 */
@Getter
@AllArgsConstructor
public enum MaterialState {
    USEING("USEING", "应用中"),
    REMOVED("REMOVED", "已下架");

    private final String value;
    private final String desc;

}