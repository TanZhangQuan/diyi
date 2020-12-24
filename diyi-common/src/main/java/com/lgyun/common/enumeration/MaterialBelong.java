package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档归属
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum MaterialBelong {
    MANAGEMENTCENTER("MANAGEMENTCENTER", "管理中心资料"),
    SERVICEPROVIDER("SERVICEPROVIDER", "服务商资料");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}