package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 主体类别
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum BodyType {
    INDIVIDUALENTERPRISE("INDIVIDUALENTERPRISE", "个独"),
    INDIVIDUALBUSINESS("INDIVIDUALBUSINESS", "个体户"),
    PARTNERSHIP("PARTNERSHIP", "合伙企业"),
    LIMITEDCOMPANY("LIMITEDCOMPANY", "有限公司");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
