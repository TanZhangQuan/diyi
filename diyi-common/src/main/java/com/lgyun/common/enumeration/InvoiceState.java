package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 平台给企业开票状态
 */
@Getter
@AllArgsConstructor
public enum InvoiceState {
    UNOPEN("UNOPEN", "未开"),
    OPENED("OPENED", "已开");

    private final String value;
    private final String desc;

}