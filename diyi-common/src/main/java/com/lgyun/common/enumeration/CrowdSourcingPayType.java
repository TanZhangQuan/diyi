package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 众包支付模式
 */
@Getter
@AllArgsConstructor
public enum CrowdSourcingPayType {
    STANDARDPAY("STANDARDPAY", "标准支付"),
    EXTENSIONPAY("EXTENSIONPAY", "扩展支付"),
    ENTERPRISEPAY("ENTERPRISEPAY", "商户代付税费");

    private final String value;
    private final String desc;

}