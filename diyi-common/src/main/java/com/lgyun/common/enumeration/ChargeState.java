package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 充值状态
 */
@Getter
@AllArgsConstructor
public enum ChargeState {
    RECHARGE("RECHARGED", "已充值"),
    REMITED("REMITED", "已打款");

    private final String value;
    private final String desc;

}