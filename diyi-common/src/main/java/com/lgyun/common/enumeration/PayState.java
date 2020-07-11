package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态
 */
@Getter
@AllArgsConstructor
public enum PayState {
    TOPAY("TOPAY", "待支付"),
    ENTAPPLYPAY("ENTAPPLYPAY", "企业已申请支付"),
    ENTPAYED("ENTPAYED", "企业已支付"),
    PLAPAYED("PLAPAYED", "平台已支付"),
    CONFIRMPAY("CONFIRMPAY", "已确认收款");

    private final String value;
    private final String desc;

}