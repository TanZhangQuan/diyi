package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 应用范围
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum ApplyScope {
    TOTAL("TOTAL", "总包开票范围"),
    CROWD("CROWD", "众包开票范围");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}