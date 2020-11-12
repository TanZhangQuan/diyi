package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 开票诉求
 */
@Getter
@AllArgsConstructor
public enum InvoiceDemand {
    SINGLE("SINGLE", "主体按一个支付清单开一张票"),
    ACCUMULATION("ACCUMULATION", "根据发票版面累积开票"),
    OTHER("OTHER", "其他");

    private final String value;
    private final String desc;

}