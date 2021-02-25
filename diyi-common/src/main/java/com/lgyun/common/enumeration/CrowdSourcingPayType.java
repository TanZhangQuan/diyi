package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 众包支付模式
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum CrowdSourcingPayType {
    STANDARDPAY("STANDARDPAY", "标准支付"),
    EXTENSIONPAY("EXTENSIONPAY", "扩展支付"),
    ENTERPRISEPAY("ENTERPRISEPAY", "商户代付税费");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}