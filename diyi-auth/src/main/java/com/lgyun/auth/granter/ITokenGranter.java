package com.lgyun.auth.granter;

import com.lgyun.common.api.R;

/**
 * 授权认证统一接口.
 *
 * @author liangfeihu
 * @since 2020/6/6 00:22
 */
public interface ITokenGranter {

    /**
     * 获取用户信息
     *
     * @param tokenParameter 授权参数
     * @return UserInfo
     */
    R grant(TokenParameter tokenParameter) throws Exception;

}
