package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 关联类型
 */
@Getter
@AllArgsConstructor
public enum ServiceProviderMakerRelType {
    TOTALSUBCONTRACTREL("TOTALSUBCONTRACTREL", "总包+分包支付关联"),
    CROWDSOURCINGREL("CROWDSOURCINGREL", "众包代开票关联");

    private final String value;
    private final String desc;

}