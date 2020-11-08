package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 子账号停用或删除
 */
@Getter
@AllArgsConstructor
public enum  ChildAccountType {
    DELETE("DELETE", "删除"),
    BLOCKUP("BLOCKUP", "停用"),
    STARTUSING("STARTUSING","启用");
    private final String value;
    private final String desc;
}
