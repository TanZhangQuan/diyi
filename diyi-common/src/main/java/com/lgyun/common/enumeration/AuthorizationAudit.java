package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author .
 * @date 2021/2/19.
 * @time 11:36.
 */
@Getter
@AllArgsConstructor
public enum AuthorizationAudit {
    AUTHORIZED("AUTHORIZED", "已授权"),
    UNAUTHORIZED("UNAUTHORIZED", "未授权");

    private final String value;
    private final String desc;
}


