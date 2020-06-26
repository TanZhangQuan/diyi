package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 协议类型
 */
@Getter
public enum AgreementType {
    intCroSouCooAgr("intCroSouCooAgr", "互联网众包项目合作协议"),
    croSouThiCooAgr("croSouThiCooAgr", "众包-三方合作协议"),
    croSouThiSerOrd("croSouThiSerOrd", "众包-三方服务订单"),
    genSouBothCooAgr("genSouBothCooAgr", "总包-双方合作协议"),
    genSouBothSerOrd("genSouBothSerOrd", "总包-双方服务订单"),
    subSouThiCooAgr("subSouThiCooAgr", "分包-三方合作协议"),
    subSouThiSerOrd("subSouThiSerOrd", "分包-三方服务订单"),
    subSouBothCooAgr("subSouBothCooAgr", "分包-双方合作协议"),
    subSouBothSerOrd("subSouBothSerOrd", "分包-双方服务订单"),
    letAutIndTaxMat("letAutIndTaxMat", "单独税务事项委托授权书"),
    powAttSepPay("powAttSepPay", "单独支付事项委托授权书");

    private String value;
    private String desc;

    AgreementType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}