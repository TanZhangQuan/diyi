package com.lgyun.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置中心 Nacos API测试
 * 只需添加注解 @RefreshScope
 *
 * @author liangfeihu
 * @since 2020/6/28 15:16
 */
@RefreshScope
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${local.cache.button: false}")
    private boolean useLocalCache;

    @Value("${local.cache.prefix: Captcha_Redis}")
    private String localCachePrefix;

    @RequestMapping("/get")
    public String get() {
        return useLocalCache + " : " + localCachePrefix;
    }

    /**
     * 获取服务实例
     */
    @GetMapping("/instances")
    public Map<String, List<ServiceInstance>> instances() {
        Map<String, List<ServiceInstance>> instances = new HashMap<>(16);
        List<String> services = discoveryClient.getServices();
        services.forEach(s -> {
            List<ServiceInstance> list = discoveryClient.getInstances(s);
            instances.put(s, list);
        });
        return instances;
    }

}
