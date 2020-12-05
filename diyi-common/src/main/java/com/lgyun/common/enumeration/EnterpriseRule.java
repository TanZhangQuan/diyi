package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商户规则要求
 */
@Getter
@AllArgsConstructor
public enum EnterpriseRule {
    BIZLICENCE("BIZLICENCE", "营业执照"),
    JOINCONTRACT("JOINCONTRACT", "加盟合同"),
    COMMITMENTLETTER("COMMITMENTLETTER", "商户承诺函");

    private final String value;
    private final String desc;

}