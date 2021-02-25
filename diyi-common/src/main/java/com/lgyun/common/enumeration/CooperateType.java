package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 合作类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum CooperateType {
    ALLOCATION("ALLOCATION", "分配"),
    CREATE("CREATE", "创建");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}