package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.system.user.entity.UserInfo;
import org.springframework.stereotype.Component;

/**
 * Feign失败配置
 *
 * @author liangfeihu
 * @since 2020/6/6 00:29
 */
@Component
public class IUserClientFallback implements IUserClient {

    @Override
    public R<UserInfo> userInfo(Long userId) {
        return R.fail("未获取到账号信息");
    }

    @Override
    public R<UserInfo> userInfo(String tenantId, String account, String password) {
        return R.fail("未获取到账号信息");
    }

}
