package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 支付状态
 */
@Getter
public enum PayState {
    TOPAY("TOPAY", "待支付"),
    ENTAPPLYPAY("ENTAPPLYPAY", "企业已申请支付"),
    ENTPAYED("ENTPAYED", "企业已支付"),
    PLAPAYED("PLAPAYED", "平台已支付"),
    CONFIRMPAY("CONFIRMPAY", "已确认收款");

    private String value;
    private String desc;

    PayState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}