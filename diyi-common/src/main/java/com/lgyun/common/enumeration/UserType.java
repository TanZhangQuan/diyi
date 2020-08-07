package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型
 *
 * @author tzq
 */
@Getter
@AllArgsConstructor
public enum UserType {
    MAKER("MAKER", "创客"),
    ENTERPRISE("ENTERPRISE", "商户"),
    ADMIN("ADMIN", "管理员");

    private final String value;
    private final String desc;

}
