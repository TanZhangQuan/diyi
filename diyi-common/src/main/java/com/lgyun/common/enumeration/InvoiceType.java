package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 发票类别
 */
@Getter
@AllArgsConstructor
public enum InvoiceType {
    ALLOPEN("ALLOPEN", "汇总代开"),
    SINGLEOPEN("SINGLEOPEN", "门征单开");

    private final String value;
    private final String desc;

}