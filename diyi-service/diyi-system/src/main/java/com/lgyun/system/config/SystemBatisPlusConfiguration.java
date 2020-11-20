package com.lgyun.system.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import com.lgyun.core.mp.plugins.SqlLogInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus 配置
 *
 * @author tzq
 * @since 2020/6/6 22:46
 */
@Configuration
@MapperScan("com.lgyun.**.mapper.**")
public class SystemBatisPlusConfiguration {

    @Bean
    @ConditionalOnMissingBean(PaginationInterceptor.class)
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * sql 日志
     *
     * @return SqlLogInterceptor
     */
    @Bean
    @ConditionalOnProperty(value = "blade.mybatis-plus.sql-log", matchIfMissing = true)
    public SqlLogInterceptor sqlLogInterceptor() {
        return new SqlLogInterceptor();
    }

}

