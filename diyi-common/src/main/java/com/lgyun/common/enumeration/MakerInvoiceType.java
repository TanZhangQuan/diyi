package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 创客发票开票类别
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum MakerInvoiceType {
    NATURALPERSONALLOPEN("NATURALPERSONALLOPEN", "自然人汇总代开"),
    NATURALPERSONSINGLEOPEN("NATURALPERSONSINGLEOPEN", "自然人门征单开"),
    INDIVIDUALBUSINESSTAXBUREAU("INDIVIDUALBUSINESSTAXBUREAU", "个体户税局代开"),
    INDIVIDUALBUSINESSSELFOPEN("INDIVIDUALBUSINESSSELFOPEN", "个体户自开"),
    INDIVIDUALENTERPRISESELFOPEN("INDIVIDUALENTERPRISESELFOPEN", "个独自开");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}