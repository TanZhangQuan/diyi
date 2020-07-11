package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 获得方式：1,抢单获得；2，派单获得
 * @author jun.
 * @date 2020/7/7.
 * @time 16:33.
 */
@Getter
@AllArgsConstructor
public enum GetType {
    GETGRABBING("GETGRABBING", "抢单获得"),
    GETDISPATCH("GETDISPATCH", "派单获得");

    private final String value;
    private final String desc;

}
