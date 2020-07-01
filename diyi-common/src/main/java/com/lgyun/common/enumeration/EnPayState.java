package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 企业支付给平台状态
 */
@Getter
public enum EnPayState {
    TOPAY("TOPAY", "待支付"),
    PAYED("PAYED", "已支付"),
    confirmPay("confirmPay", "已确认收款");

    private String value;
    private String desc;

    EnPayState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}