package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 创客支付给平台状态
 */
@Getter
@AllArgsConstructor
public enum MakerPayState {
    TOPAY("TOPAY", "待支付"),
    ENTERPRISETOPAY("ENTERPRISETOPAY", "企业已申请支付"),
    ENTERPRISEPAID("ENTERPRISEPAID", "企业已支付"),
    PLATFORMPAID("PLATFORMPAID", "平台已支付"),
    CONFIRMPAID("CONFIRMPAID", "已确认收款");

    private final String value;
    private final String desc;

}