package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 创客类别
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum MakerType {
    NATURALPERSON("NATURALPERSON", "自然人"),
    INDIVIDUALENTERPRISE("INDIVIDUALENTERPRISE", "个独"),
    INDIVIDUALBUSINESS("INDIVIDUALBUSINESS", "个体户");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
