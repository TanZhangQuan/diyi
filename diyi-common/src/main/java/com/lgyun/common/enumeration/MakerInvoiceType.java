package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 创客发票开票类别
 */
@Getter
@AllArgsConstructor
public enum MakerInvoiceType {
    NATURALPERSONALLOPEN("NATURALPERSONALLOPEN", "自然人汇总代开"),
    NATURALPERSONSINGLEOPEN("NATURALPERSONSINGLEOPEN", "自然人门征单开"),
    INDIVIDUALBUSINESSTAXBUREAU("INDIVIDUALBUSINESSTAXBUREAU", "个体户税务局代开"),
    INDIVIDUALBUSINESSSELFOPEN("INDIVIDUALBUSINESSSELFOPEN", "个体户自开"),
    INDIVIDUALENTERPRISESELFOPEN("INDIVIDUALENTERPRISESELFOPEN", "个独自开");

    private final String value;
    private final String desc;

}