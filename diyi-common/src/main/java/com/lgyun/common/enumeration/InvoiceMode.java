package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 交付支付验收单类型
 */
@Getter
@AllArgsConstructor
public enum InvoiceMode {
    ISSUEDINFULL("ISSUEDINFULL", "已全额开具"),
    PARTIALLYISSUED("PARTIALLYISSUED", "已部分开具");

    private final String value;
    private final String desc;

}
