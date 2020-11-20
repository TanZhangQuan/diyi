package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 局类型
 */
@Getter
@AllArgsConstructor
public enum RelBureauType {
    TAXBUREAU("TAXBUREAU", "税务局"),
    MARSUPANDADM("MARSUPANDADM", "市场监督管理局"),
    INDUSTRIALPARKS("INDUSTRIALPARKS", "产业园区"),
    PAYINGAGENCY("PAYINGAGENCY", "支付机构");
    private final String value;
    private final String desc;
}
