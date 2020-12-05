package com.lgyun.common.constant;

/**
 * AppConstant
 *
 * @author tzq
 * @since 2020/6/2 23:32
 */
public interface AppConstant {

    /**
     * 应用版本
     */
    String APPLICATION_VERSION = "0.0.1-SNAPSHOT";

    /**
     * 基础包
     */
    String BASE_PACKAGES = "com.lgyun";

    /**
     * 应用名前缀
     */
    String APPLICATION_NAME_PREFIX = "diyi-";

    /**
     * 网关模块名称
     */
    String APPLICATION_GATEWAY_NAME = APPLICATION_NAME_PREFIX + "gateway";

    /**
     * 授权模块名称
     */
    String APPLICATION_AUTH_NAME = APPLICATION_NAME_PREFIX + "auth";

    /**
     * 系统模块名称
     */
    String APPLICATION_SYSTEM_NAME = APPLICATION_NAME_PREFIX + "system";

    /**
     * 用户模块名称
     */
    String APPLICATION_USER_NAME = APPLICATION_NAME_PREFIX + "user";

    /**
     * 订单模块名称
     */
    String APPLICATION_ORDER_NAME = APPLICATION_NAME_PREFIX + "order";

    /**
     * 开发环境
     */
    String DEV_CODE = "dev";

    /**
     * 生产环境
     */
    String PROD_CODE = "prod";

    /**
     * 测试环境
     */
    String TEST_CODE = "test";

    /**
     * 代码部署于 linux 上，工作默认为 mac 和 Windows
     */
    String OS_NAME_LINUX = "LINUX";

}
