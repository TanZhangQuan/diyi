package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档属性
 */
@Getter
@AllArgsConstructor
public enum OpenAtribute {
    GLOBALPUBLIC("GLOBALPUBLIC", "全局公开"),
    MANAGEMENTCENTER("MANAGEMENTCENTER", "仅对管理中心公开");

    private final String value;
    private final String desc;

}