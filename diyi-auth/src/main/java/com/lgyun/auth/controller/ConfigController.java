package com.lgyun.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置中心 Nacos API测试
 *
 * @author liangfeihu
 * @since 2020/6/28 15:16
 */
@RefreshScope
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${local.cache.button: false}")
    private boolean useLocalCache;

    @Value("${local.cache.prefix: Captcha_Redis}")
    private String localCachePrefix;

    @RequestMapping("/get")
    public String get() {
        return useLocalCache + " : " + localCachePrefix;
    }

}
