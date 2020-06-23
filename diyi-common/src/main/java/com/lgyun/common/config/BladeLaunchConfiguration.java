package com.lgyun.common.config;

import com.lgyun.common.support.BladeProperties;
import lombok.AllArgsConstructor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 配置类
 *
 * @author liangfeihu
 * @since 2020/6/5 23:30
 */
@Configuration
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties({
	BladeProperties.class
})
public class BladeLaunchConfiguration {

}
