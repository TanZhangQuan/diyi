package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 短视频审核状态
 */
@Getter
public enum VideoAudit {
    TOAUDIT("TOAUDIT", "未审核"),
    AUDITPASS("AUDITPASS", "审核通过"),
    AUDITFAIL("AUDITFAIL", "审核未通过");

    private String value;
    private String desc;

    VideoAudit(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}