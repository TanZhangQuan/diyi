package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 开票状态
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum InvoicePrintState {
    TOAPPLY("TOAPPLY", "待申请"),
    APPLYING("APPLYING", "申请中"),
    INVOICEING("INVOICEING", "开票中"),
    INVOICESUCCESS("INVOICESUCCESS", "已开票"),
    INVOICEFAIL("INVOICEFAIL", "开票失败");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}