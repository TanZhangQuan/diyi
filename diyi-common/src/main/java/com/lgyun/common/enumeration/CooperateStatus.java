package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 合作状态
 */
@Getter
@AllArgsConstructor
public enum CooperateStatus {
    COOPERATING("COOPERATING", "合作中"),
    COOPERATESTOP("COOPERATESTOP", "停止合作"),
    SERVICEPROVIDEROFFTHESHELF("","服务商下架停止合作");

    private final String value;
    private final String desc;

}