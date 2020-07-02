package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 纸质协议上传状态
 */
@Getter
public enum PaaState {
    UNUPLOAD("UNUPLOAD", "未上传"),
    UPLOADED("UPLOADED", "已上传");

    private String value;
    private String desc;

    PaaState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}