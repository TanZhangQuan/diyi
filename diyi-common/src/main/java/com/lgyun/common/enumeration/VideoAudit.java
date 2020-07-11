package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短视频审核状态
 */
@Getter
@AllArgsConstructor
public enum VideoAudit {
    TOAUDIT("TOAUDIT", "未审核"),
    AUDITPASS("AUDITPASS", "审核通过"),
    AUDITFAIL("AUDITFAIL", "审核未通过");

    private final String value;
    private final String desc;

}