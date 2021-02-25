package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
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
@SwaggerDisplayEnum()
public enum SignPower {
    PARTYA("PARTYA", "甲方"),
    PARTYB("PARTYB", "乙方");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
