package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 订单状态
 */
@Getter
public enum OrderState {
    grabOrdering("grabOrdering", "抢单中"),
    orderClosed("orderClosed", "已关单"),
    paying("paying", "支付中"),
    payed("payed", "已支付");

    private String value;
    private String desc;

    OrderState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}