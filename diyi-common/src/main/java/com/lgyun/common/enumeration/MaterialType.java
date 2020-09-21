package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档属性
 */
@Getter
@AllArgsConstructor
public enum MaterialType {
    TEMPLATE("TEMPLATE", "模板文件"),
    COMMON("COMMON", "普通文件");

    private final String value;
    private final String desc;

}