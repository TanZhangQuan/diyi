package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账号状态
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum AccountState {
    NORMAL("NORMAL", "正常状态"),
    FREEZE("FREEZE", "冻结状态"),
    ILLEGAL("ILLEGAL", "非法状态");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}