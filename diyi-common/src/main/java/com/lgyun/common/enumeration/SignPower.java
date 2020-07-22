package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 签字对象性质
 * 甲方；2，乙方；3，丙方；4，丁方
 * @author .
 * @date 2020/7/22.
 * @time 9:23.
 */
@Getter
@AllArgsConstructor
public enum SignPower {
    PARTYA("PARTYA", "甲方"),
    PARTYB("PARTYB", "乙方"),
    PARTYC("PARTYC", "丙方"),
    PARTYD("PARTYD", "丁方");

    private final String value;
    private final String desc;
}
