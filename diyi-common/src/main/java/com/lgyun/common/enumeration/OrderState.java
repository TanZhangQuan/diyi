package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态
 */
@Getter
@AllArgsConstructor
public enum OrderState {
    GRABORDERING("GRABORDERING", "抢单中"),
    ORDERCLOSED("ORDERCLOSED", "已关单"),
    PAYING("PAYING", "支付中"),
    PAYED("PAYED", "已支付");

    private final String value;
    private final String desc;

}