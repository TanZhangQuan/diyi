package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 创客类别
 */
@Getter
@AllArgsConstructor
public enum MakerType {
    NATURALPERSON("NATURALPERSON", "自然人"),
    INDIVIDUALENTERPRISE("INDIVIDUALENTERPRISE", "个独"),
    INDIVIDUALBUSINESS("INDIVIDUALBUSINESS", "个体户");

    private final String value;
    private final String desc;

}
