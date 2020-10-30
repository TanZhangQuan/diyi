package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 总包支付清单支付状态
 */
@Getter
@AllArgsConstructor
public enum PayEnterprisePayState {
    TOPAY("TOPAY", "总包待支付"),
    PAYED("PAYED", "总包已支付"),
    CONFIRMPAY("CONFIRMPAY", "创客已确认收款");

    private final String value;
    private final String desc;

}