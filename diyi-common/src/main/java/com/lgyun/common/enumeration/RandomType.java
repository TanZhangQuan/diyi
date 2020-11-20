package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 生成的随机数类型
 *
 * @author tzq
 * @since 2020/6/5 17:49
 */
@Getter
@AllArgsConstructor
public enum RandomType {
    INT("INT", "数字"),
    STRING("STRING", "英文"),
    ALL("ALL", "数字英文混合");

    private final String value;
    private final String desc;

}
