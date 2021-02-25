package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交付支付验收单类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum InvoiceMode {
    ISSUEDINFULL("ISSUEDINFULL", "已全额开具"),
    PARTIALLYISSUED("PARTIALLYISSUED", "已部分开具");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
