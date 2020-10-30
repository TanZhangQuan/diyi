package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 创客支付明细支付状态
 */
@Getter
@AllArgsConstructor
public enum PayMakerPayState {
    TOPAY("TOPAY", "总包待支付"),
    ENTERPRISETOPAY("ENTERPRISETOPAY", "商户已申请支付"),
    ENTERPRISEPAID("ENTERPRISEPAID", "总包已支付"),
    PLATFORMPAID("PLATFORMPAID", "分包已支付"),
    CONFIRMPAID("CONFIRMPAID", "创客已确认收款");

    private final String value;
    private final String desc;

}