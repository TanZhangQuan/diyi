package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuType {
    ADMIN("ADMIN","平台端的菜单"),
    AGENTMAIN("AGENTMAIN","渠道商端的菜单"),
    ENTERPRISE("ENTERPRISE", "商户端的菜单"),
    SERVICEPROVIDER("SERVICEPROVIDER", "服务商端的菜单");

    private final String value;
    private final String desc;
}
