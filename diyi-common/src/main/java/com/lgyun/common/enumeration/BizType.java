package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 税种
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum BizType {
    SMALL("SMALL", "小规模"),
    TAXPAYER("TAXPAYER", "一般纳税人");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}