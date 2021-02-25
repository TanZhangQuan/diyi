package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 获得方式：1,抢单获得；2，派单获得
 * @author tzq
 * @date 2020/7/7.
 * @time 16:33.
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum GetType {
    GETGRABBING("GETGRABBING", "抢单获得"),
    GETDISPATCH("GETDISPATCH", "派单获得");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
