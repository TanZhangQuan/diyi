package com.lgyun.system.user.feign;

import com.lgyun.common.api.R;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.entity.User;
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

    /**
     * 获取用户信息
     *
     * @param phone 用户手机号
     * @return
     */
    @Override
    public R<UserInfo> userInfoByPhone(String phone) {
        return R.fail("未获取到账号信息");
    }

    @Override
    public MakerEntity makerFindByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public MakerEntity makerFindByOpenid(String openid) {
        return null;
    }

    @Override
    public User makerSaveOrUpdate(String openid, String sessionKey, String purePhoneNumber) {
        return null;
    }

    @Override
    public User userFindById(Long id) {
        return null;
    }

}
