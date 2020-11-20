package com.lgyun.common.tenant;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 多租户配置类
 *
 * @author tzq
 */
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(BladeTenantProperties.class)
public class TenantConfiguration {

    /**
     * 多租户配置类
     */
    private final BladeTenantProperties properties;

    /**
     * 自定义租户id生成器
     *
     * @return TenantId
     */
    @Bean
    @ConditionalOnMissingBean(TenantId.class)
    public TenantId tenantId() {
        return new BladeTenantId();
    }

}
