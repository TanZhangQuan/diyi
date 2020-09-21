package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档归属
 */
@Getter
@AllArgsConstructor
public enum MaterialBelong {
    MANAGEMENTCENTER("MANAGEMENTCENTER", "管理中心资料"),
    SERVICEPROVIDER("SERVICEPROVIDER", "服务商资料");

    private final String value;
    private final String desc;

}