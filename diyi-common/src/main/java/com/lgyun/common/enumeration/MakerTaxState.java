package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 创客完税证明开票状态
 */
@Getter
public enum MakerTaxState {
    unOpen("unOpen", "未开"),
    opened("opened", "已开");

    private String value;
    private String desc;

    MakerTaxState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}