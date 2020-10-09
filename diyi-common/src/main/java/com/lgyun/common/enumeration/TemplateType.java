package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 签署文件模板类型
 */
@Getter
@AllArgsConstructor
public enum TemplateType {
    CONTRACT("CONTRACT", "合同"),
    AUTHORIZATION("AUTHORIZATION", "授权");

    private final String value;
    private final String desc;

}