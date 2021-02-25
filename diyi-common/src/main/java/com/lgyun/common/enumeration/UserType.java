package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型
 *
 * @author tzq
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum UserType {
    MAKER("MAKER", "创客"),
    ADMIN("ADMIN", "管理员"),
    PARTNER("PARTNER", "合伙人"),
    AGENTMAIN("AGENTMAIN","渠道商"),
    ENTERPRISE("ENTERPRISE", "商户"),
    SERVICEPROVIDER("SERVICEPROVIDER", "服务商"),
    RELBUREAU("RELBUREAU","相关局");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
