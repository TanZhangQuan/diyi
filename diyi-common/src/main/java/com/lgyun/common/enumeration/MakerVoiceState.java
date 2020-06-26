package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 创客发票开票状态
 */
@Getter
public enum MakerVoiceState {
    unOpen("unOpen", "未开"),
    opened("opened", "已开");

    private String value;
    private String desc;

    MakerVoiceState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}