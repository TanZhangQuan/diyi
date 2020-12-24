package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 合同有效性
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum ValidState {
    TOVALID("TOVALID", "待生效"),
    VALIDING("VALIDING", "生效中"),
    UNVALID("UNVALID", "已失效");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}