package com.lgyun.gateway.provider;

import com.lgyun.common.constant.TokenConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置, 配置系统放行API
 *
 * @author liangfeihu
 * @since 2020/6/2 23:34
 */
public class AuthProvider {

    public static String TARGET = "/**";
    public static String REPLACEMENT = "";
    public static String AUTH_KEY = TokenConstant.HEADER;
    private static List<String> defaultSkipUrl = new ArrayList<>();

    static {
        defaultSkipUrl.add("/example");
        defaultSkipUrl.add("/actuator/health/**");
        defaultSkipUrl.add("/v2/api-docs/**");
        defaultSkipUrl.add("/v2/api-docs-ext/**");
        defaultSkipUrl.add("/auth/**");
        defaultSkipUrl.add("/log/**");
        defaultSkipUrl.add("/menu/routes");
        defaultSkipUrl.add("/menu/auth-routes");
        defaultSkipUrl.add("/order/create/**");
        defaultSkipUrl.add("/storage/deduct/**");
        defaultSkipUrl.add("/error/**");
        defaultSkipUrl.add("/assets/**");
        defaultSkipUrl.add("/maker/face_ocr_notify");
        defaultSkipUrl.add("/maker/mobile_ocr_notify");
        defaultSkipUrl.add("/maker/bank_card_ocr_notify");
        defaultSkipUrl.add("/maker/update-password");
        defaultSkipUrl.add("/web/enterprise_worker/update-password");
        defaultSkipUrl.add("/web/service_provider_worker/update-password");
        defaultSkipUrl.add("/web/user/update-password");
    }

    /**
     * 默认无需鉴权的API
     */
    public static List<String> getDefaultSkipUrl() {
        return defaultSkipUrl;
    }

}
