package com.lgyun.common.enumeration;

/**
 * 获得方式：1,抢单获得；2，派单获得
 * @author jun.
 * @date 2020/7/7.
 * @time 16:33.
 */
public enum GetType {
    GETGRABBING("GETGRABBING", "抢单获得"),
    GETDISPATCH("GETDISPATCH", "派单获得");

    private String value;
    private String desc;

    GetType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
