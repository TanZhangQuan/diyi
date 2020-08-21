package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 协议类别
 */
@Getter
@AllArgsConstructor
public enum AgreementType {
    MAKERJOINAGREEMENT("MAKERJOINAGREEMENT", "创客加盟协议"),
    AGENTJOINAGREEMENT("AGENTJOINAGREEMENT", "渠道商加盟协议"),
    PARTNERJOINAGREEMENT("PARTNERJOINAGREEMENT", "合伙人加盟协议"),
    MAKERPOWERATTORNEY("MAKERPOWERATTORNEY", "创客授权书"),
    ENTMAKSUPPLEMENTARYAGREEMENT("ENTMAKSUPPLEMENTARYAGREEMENT", "商户-创客补充协议"),
    SERENTSUPPLEMENTARYAGREEMENT("SERENTSUPPLEMENTARYAGREEMENT", "服务商-商户补充协议"),
    MAKERTAXPOWERATTORNEY("MAKERTAXPOWERATTORNEY", "创客单独税务事项委托授权书"),
    MAKERPAYPOWERATTORNEY("MAKERPAYPOWERATTORNEY", "创客单独支付事项委托授权书"),
    OTHERAGREEMENT("OTHERAGREEMENT", "其他协议");

    private final String value;
    private final String desc;

}