package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 支付状态
 */
@Getter
public enum PayState {
    toPay("toPay", "待支付"),
    entApplyPay("entApplyPay", "企业已申请支付"),
    entPayed("entPayed", "企业已支付"),
    plaPayed("plaPayed", "平台已支付"),
    confirmPay("confirmPay", "已确认收款");

    private String value;
    private String desc;

    PayState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}