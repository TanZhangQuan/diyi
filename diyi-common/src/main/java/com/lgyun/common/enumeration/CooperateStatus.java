package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 合作状态
 */
@Getter
public enum CooperateStatus {
    COOPERATING("COOPERATING", "合作中"),
    COOPERATESTOP("COOPERATESTOP", "停止合作");

    private String value;
    private String desc;

    CooperateStatus(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}