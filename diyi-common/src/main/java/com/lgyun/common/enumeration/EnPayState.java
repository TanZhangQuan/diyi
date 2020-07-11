package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 企业支付给平台状态
 */
@Getter
@AllArgsConstructor
public enum EnPayState {
    TOPAY("TOPAY", "待支付"),
    PAYED("PAYED", "已支付"),
    confirmPay("confirmPay", "已确认收款");

    private final String value;
    private final String desc;

}