package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付类型
 * @author jun.
 * @date 2020/7/2.
 * @time 14:08.
 */
@Getter
@AllArgsConstructor
public enum PaymentType {
    ONLINE("ONLINE", "在线支付"),
    OFFLINE("OFFLINE", "线下支付");

    private final String value;
    private final String desc;

}
