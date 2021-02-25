package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 合作状态
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum CooperateStatus {
    COOPERATING("COOPERATING", "合作中"),
    COOPERATESTOP("COOPERATESTOP", "停止合作");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}