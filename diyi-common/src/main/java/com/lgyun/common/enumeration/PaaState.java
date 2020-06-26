package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 纸质协议上传状态
 */
@Getter
public enum PaaState {
    unUpload("unUpload", "未上传"),
    uploaded("uploaded", "已上传");

    private String value;
    private String desc;

    PaaState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}