package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 支付类型
 * @author jun.
 * @date 2020/7/2.
 * @time 14:08.
 */
@Getter
public enum PaymentType {
    ONLINE("ONLINE", "在线支付"),
    OFFLINE("OFFLINE", "线下支付");

    private String value;
    private String desc;

    PaymentType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
