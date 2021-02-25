package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短视频审核状态
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum VideoAudit {
    TOAUDIT("TOAUDIT", "未审核"),
    AUDITPASS("AUDITPASS", "审核通过"),
    AUDITFAIL("AUDITFAIL", "审核未通过");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}