package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * http请求方法
 */
@Getter
public enum HttpMethod {
    POST("POST", "POST方法"),
    GET("GET", "GET方法");

    private String value;
    private String desc;

    HttpMethod(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}