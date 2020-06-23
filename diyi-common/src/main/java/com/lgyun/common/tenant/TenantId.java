package com.lgyun.common.tenant;

/**
 * 租户id生成器
 *
 * @author liangfeihu
 * @since 2020/6/5 17:43
 */
public interface TenantId {

    /**
     * 生成自定义租户id
     *
     * @return string
     */
    String generate();

}
