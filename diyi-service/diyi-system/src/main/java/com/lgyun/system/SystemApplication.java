package com.lgyun.system;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.common.tenant.BladeTenantId;
import com.lgyun.common.tenant.TenantId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * 系统模块服务器
 *
 * @author liangfeihu
 * @since 2020/6/6 23:08
 */
@Slf4j
@SpringBootApplication(scanBasePackages = AppConstant.BASE_PACKAGES)
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class SystemApplication {

    public static void main(String[] args) {
        log.info("系统模块服务启动开始");
        BladeApplication.run(AppConstant.APPLICATION_SYSTEM_NAME, SystemApplication.class, args);
        log.info("系统模块服务启动结束");
    }


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

