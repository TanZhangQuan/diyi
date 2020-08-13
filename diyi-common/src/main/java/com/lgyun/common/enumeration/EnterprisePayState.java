package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 企业支付给平台状态
 */
@Getter
@AllArgsConstructor
public enum EnterprisePayState {
    TOPAY("TOPAY", "待支付"),
    PAYED("PAYED", "已支付"),
    CONFIRMPAY("CONFIRMPAY", "已确认收款");

    private final String value;
    private final String desc;

}