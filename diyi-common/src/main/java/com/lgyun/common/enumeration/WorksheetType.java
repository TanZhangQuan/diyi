package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类型，总包+分包，众包/众采
 * @author tzq
 * @date 2020/7/7.
 * @time 15:11.
 */
@Getter
@AllArgsConstructor
public enum WorksheetType {
    SUBPACKAGE("SUBPACKAGE", "总包+分包"),
    CROWDSOURCED("CROWDSOURCED", "众包/众采");

    private final String value;
    private final String desc;

}
