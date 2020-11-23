package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 合作类型
 */
@Getter
@AllArgsConstructor
public enum CooperateType {
    ALLOCATION("ALLOCATION", "分配"),
    CREATE("CREATE", "创建");

    private final String value;
    private final String desc;

}