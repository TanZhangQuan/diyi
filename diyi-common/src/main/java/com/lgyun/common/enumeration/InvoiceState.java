package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 平台给企业开票状态
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum InvoiceState {
    UNOPEN("UNOPEN", "未开"),
    OPENED("OPENED", "已开");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}