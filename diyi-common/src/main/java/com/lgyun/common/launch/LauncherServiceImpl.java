package com.lgyun.common.launch;

import com.lgyun.common.constant.LauncherConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

/**
 * 启动参数拓展
 *
 * @author liangfeihu
 * @since 2020/6/3 00:01
 */
@Slf4j
public class LauncherServiceImpl implements LauncherService {

    @Override
    public void launcher(SpringApplicationBuilder builder, String appName, String profile) {
        String nacosAddr = LauncherConstant.nacosAddr(profile);
        Properties props = System.getProperties();
        props.setProperty("spring.cloud.nacos.discovery.server-addr", nacosAddr);
        props.setProperty("spring.cloud.nacos.config.server-addr", nacosAddr);
        //props.setProperty("spring.cloud.sentinel.transport.dashboard", LauncherConstant.sentinelAddr(profile));
        //props.setProperty("spring.zipkin.base-url", LauncherConstant.zipkinAddr(profile));
        log.info("[launcher] nacos addr={}", nacosAddr);
    }

}
