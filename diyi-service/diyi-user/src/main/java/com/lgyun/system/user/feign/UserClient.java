package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务Feign实现类
 *
 * @author liangfeihu
 * @since 2020/6/6 22:11
 */
@RestController
@AllArgsConstructor
public class UserClient implements IUserClient {

    private IUserService service;

    @Override
    @GetMapping(API_PREFIX + "/user-info-by-id")
    public R<UserInfo> userInfo(Long userId) {
        return R.data(service.userInfo(userId));
    }

    /**
     * 获取用户信息
     *
     * @param phone 用户手机号
     * @return
     */
    @Override
    @GetMapping(API_PREFIX + "/phone")
    public R<UserInfo> userInfoByPhone(String phone) {
		UserInfo userInfo = service.userInfoByPhone(phone);
		return R.data(userInfo);
    }

    @Override
    @GetMapping(API_PREFIX + "/user-info")
    public R<UserInfo> userInfo(String tenantId, String account, String password) {
        return R.data(service.userInfo(tenantId, account, password));
    }

}
