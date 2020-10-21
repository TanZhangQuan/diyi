package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 相关局匹配的服务商状态
 */
@Getter
@AllArgsConstructor
public enum BureauServiceProviderStatus {
    OPEN("OPEN", "开启"),
    CLOSE("CLOSE", "关闭");
    private final String value;
    private final String desc;
}
