package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 订单支付方式
 */
@Getter
public enum OrderPayType {
    genSubSouStaPay("genSubSouStaPay", "总包+分包标准支付"),
    genSubSouSelfPay("genSubSouSelfPay", "总包+分包自助支付"),
    croSouEntSelfPay("croSouEntSelfPay", "众包企业自行支付"),
    croSouPlaColPay("croSouPlaColPay", "众包平台代收代付"),
    genSubSouTongLianPay("genSubSouTonLiaPay", "总包+分包通联支付"),
    colSouTongLianPay("colSouTonLiaPay", "众包通联支付");

    private String value;
    private String desc;

    OrderPayType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}