package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * http请求方法
 */
@Getter
@AllArgsConstructor
public enum HttpMethod {
    POST("POST", "POST方法"),
    GET("GET", "GET方法");

    private final String value;
    private final String desc;

}