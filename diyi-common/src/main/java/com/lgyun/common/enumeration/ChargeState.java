package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 充值状态
 */
@Getter
public enum ChargeState {
    RECHARGE("RECHARGED", "已充值"),
    REMITED("REMITED", "已打款");

    private String value;
    private String desc;

    ChargeState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}