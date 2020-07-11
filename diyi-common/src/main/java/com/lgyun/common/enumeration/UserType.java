package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型
 */
@Getter
@AllArgsConstructor
public enum UserType {
    MAKER("MAKER", "创客"),
    ADMIN("ADMIN", "管理员");

    private final String value;
    private final String desc;

}