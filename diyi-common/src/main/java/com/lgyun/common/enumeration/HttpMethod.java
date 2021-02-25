package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * http请求方法
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum HttpMethod {
    POST("POST", "POST方法"),
    GET("GET", "GET方法");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}