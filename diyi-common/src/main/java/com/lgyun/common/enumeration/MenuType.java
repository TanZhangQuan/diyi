package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单端口
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum MenuType {
    ADMIN("ADMIN","平台端的菜单"),
    AGENTMAIN("AGENTMAIN","渠道商端的菜单"),
    ENTERPRISE("ENTERPRISE", "商户端的菜单"),
    SERVICEPROVIDER("SERVICEPROVIDER", "服务商端的菜单");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
