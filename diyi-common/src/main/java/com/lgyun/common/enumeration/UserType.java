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
    ADMIN("ADMIN", "管理员"),
    MAKER("MAKER", "创客"),
    ENTERPRISE("ENTERPRISE", "商户"),
    SERVICEPROVIDER("SERVICEPROVIDER", "服务商"),
    AGENTMAIN("AGENTMAIN","渠道商");

    private final String value;
    private final String desc;

}
