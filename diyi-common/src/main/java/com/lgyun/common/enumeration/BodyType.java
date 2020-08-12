package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 主体类别
 */
@Getter
@AllArgsConstructor
public enum BodyType {
    INDIVIDUALENTERPRISE("INDIVIDUALENTERPRISE", "个独"),
    INDIVIDUALBUSINESS("INDIVIDUALBUSINESS", "个体户"),
    PARTNERSHIP("PARTNERSHIP", "合伙企业"),
    LIMITEDCOMPANY("LIMITEDCOMPANY", "有限公司");

    private final String value;
    private final String desc;

}
