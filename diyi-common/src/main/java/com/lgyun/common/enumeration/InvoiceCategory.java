package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 发票类型
 */
@Getter
@AllArgsConstructor
public enum InvoiceCategory {
    SPECIALTICKET("SPECIALTICKET", "专票"),
    GENERALTICKET("GENERALTICKET", "普票");

    private final String value;
    private final String desc;
}
