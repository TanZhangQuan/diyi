package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 总包支付清单支付状态
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum PayEnterprisePayState {
    TOPAY("TOPAY", "总包待支付"),
    PAYED("PAYED", "总包已支付"),
    CONFIRMPAY("CONFIRMPAY", "创客已确认收款");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}