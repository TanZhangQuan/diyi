package com.lgyun.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 *
 * @author liangfeihu
 * @since 2020/6/6 01:04
 */
@Getter
@AllArgsConstructor
public enum BladeUserEnum {

    /**
     * web
     */
    WEB("web", 1),

    /**
     * app
     */
    APP("app", 2),
    ;

    final String name;
    final int category;

}
