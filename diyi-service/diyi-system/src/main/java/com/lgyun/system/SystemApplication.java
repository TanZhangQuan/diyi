package com.lgyun.system;

import com.lgyun.common.BladeApplication;
import com.lgyun.common.constant.AppConstant;
import com.lgyun.common.tenant.BladeTenantId;
import com.lgyun.common.tenant.TenantId;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * 系统模块启动器
 *
 * @author liangfeihu
 * @since 2020/6/6 23:08
 */
@SpringCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class SystemApplication {

    public static void main(String[] args) {
        BladeApplication.run(AppConstant.APPLICATION_SYSTEM_NAME, SystemApplication.class, args);
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

