package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 创客商户关系
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum RelationshipType {
    RELEVANCE("RELEVANCE", "关联"),
    ATTENTION("ATTENTION", "关注"),
    NORELATION("NORELATION", "不关联不关注");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}