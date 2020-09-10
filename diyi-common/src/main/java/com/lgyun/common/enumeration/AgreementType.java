package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 协议类别
 * 1, 创客加盟协议；
 * 2，商户加盟协议；
 * 3，服务商加盟协议；
 * 4，渠道商加盟协议；
 * 5、合伙人加盟协议；
 * 6、园区合作协议；
 * 7、税局合作协议；
 * 8、工商合作协议；
 * 9、创客授权书；
 * 10、商户-创客补充协议；
 * 11、服务商-商户补充协议；
 * 12、创客单独税务事项委托授权书；
 * 13、创客单独支付事项委托授权书；
 * 14、商户授权书；
 * 100、其他协议
 */
@Getter
@AllArgsConstructor
public enum AgreementType {
    MAKERJOINAGREEMENT("MAKERJOINAGREEMENT", "创客加盟协议"),
    ENTERPRISEJOINAGREEMENT("ENTERPRISEJOINAGREEMENT", "商户加盟协议"),
    SERVICEPROVIDERJOINAGREEMENT("SERVICEPROVIDERJOINAGREEMENT", "服务商加盟协议"),
    AGENTJOINAGREEMENT("AGENTJOINAGREEMENT", "渠道商加盟协议"),
    PARTNERJOINAGREEMENT("PARTNERJOINAGREEMENT", "合伙人加盟协议"),
    PARKAGREEMENT("PARKAGREEMENT", "园区合作协议"),
    TAXBUREAUAGREEMENT("TAXBUREAUAGREEMENT", "税局合作协议"),
    BUSINESSCIRCLESAGREEMENT("BUSINESSCIRCLESAGREEMENT", "工商合作协议"),
    MAKERPOWERATTORNEY("MAKERPOWERATTORNEY", "创客授权书"),
    ENTMAKSUPPLEMENTARYAGREEMENT("ENTMAKSUPPLEMENTARYAGREEMENT", "商户-创客补充协议"),
    SERENTSUPPLEMENTARYAGREEMENT("SERENTSUPPLEMENTARYAGREEMENT", "服务商-商户补充协议"),
    MAKERTAXPOWERATTORNEY("MAKERTAXPOWERATTORNEY", "创客单独税务事项委托授权书"),
    MAKERPAYPOWERATTORNEY("MAKERPAYPOWERATTORNEY", "创客单独支付事项委托授权书"),
    ENTERPRISEPOWERATTORNEY("ENTERPRISEPOWERATTORNEY", "商户授权书"),
    OTHERAGREEMENT("OTHERAGREEMENT", "其他协议");

    private final String value;
    private final String desc;

}
