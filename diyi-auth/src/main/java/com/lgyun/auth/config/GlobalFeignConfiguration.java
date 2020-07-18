package com.lgyun.auth.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign 日志
 *
 * @author liangfeihu
 * @since 2020/7/16 23:35
 */
@Configuration
public class GlobalFeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
