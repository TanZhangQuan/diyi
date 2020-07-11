package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 纸质协议上传状态
 */
@Getter
@AllArgsConstructor
public enum PaaState {
    UNUPLOAD("UNUPLOAD", "未上传"),
    UPLOADED("UPLOADED", "已上传");

    private final String value;
    private final String desc;

}