package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单支付方式
 */
@Getter
@AllArgsConstructor
public enum OrderPayType {
    GENSUBSOUSTAPAY("GENSUBSOUSTAPAY", "总包+分包标准支付"),
    GENSUBSOUSELFPAY("GENSUBSOUSELFPAY", "总包+分包自助支付"),
    CROSOUENTSELFPAY("CROSOUENTSELFPAY", "众包企业自行支付"),
    CROSOUPLACOLPAY("CROSOUPLACOLPAY", "众包平台代收代付"),
    GENSUBSOUTONGLIANPAY("GENSUBSOUTONLIAPAY", "总包+分包通联支付"),
    COLSOUTONGLIANPAY("COLSOUTONLIAPAY", "众包通联支付");

    private final String value;
    private final String desc;

}