package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模式，派单、抢单、混合（默认：混合型）
 * @author tzq
 * @date 2020/7/7.
 * @time 15:11.
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum WorksheetMode {
    DISPATCH("DISPATCH", "派单"),
    GRABBING("GRABBING", "抢单"),
    BLEND("BLEND", "混合");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
