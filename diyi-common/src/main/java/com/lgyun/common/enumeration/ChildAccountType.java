package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 子账号停用或删除
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum  ChildAccountType {
    DELETE("DELETE", "删除"),
    BLOCKUP("BLOCKUP", "停用"),
    STARTUSING("STARTUSING","启用");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
