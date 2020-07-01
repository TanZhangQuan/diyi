package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 订单状态
 */
@Getter
public enum OrderState {
    GRABORDERING("GRABORDERING", "抢单中"),
    ORDERCLOSED("ORDERCLOSED", "已关单"),
    PAYING("PAYING", "支付中"),
    PAYED("PAYED", "已支付");

    private String value;
    private String desc;

    OrderState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}