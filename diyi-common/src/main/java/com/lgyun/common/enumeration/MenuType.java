package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档属性
 */
@Getter
@AllArgsConstructor
public enum MenuType {

    ENTERPRISE("ENTERPRISE", "商户端的菜单"),
    ADMIN("ADMIN","平台端的菜单"),
    SERVICEPROVIDER("SERVICEPROVIDER", "服务商端的菜单");

    private final String value;
    private final String desc;
}
