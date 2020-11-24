package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型
 *
 * @author tzq
 */
@Getter
@AllArgsConstructor
public enum UserType {
    ADMIN("ADMIN", "管理员"),
    MAKER("MAKER", "创客"),
    ENTERPRISE("ENTERPRISE", "商户"),
    SERVICEPROVIDER("SERVICEPROVIDER", "服务商"),
    AGENTMAIN("AGENTMAIN","渠道商"),
    PARTNER("PARTNER", "合伙人"),
    TAXBUREAU("TAXBUREAU","税务局"),
    MARKETSUPERVISION("MARKETSUPERVISION","市场监督管理局"),
    INDUSTRIALPARKS("INDUSTRIALPARKS","产业园区"),
    PAYMENTAGENCY("PAYMENTAGENCY","支付机构");

    private final String value;
    private final String desc;

}
