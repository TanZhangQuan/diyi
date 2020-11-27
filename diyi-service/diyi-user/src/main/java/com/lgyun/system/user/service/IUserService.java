package com.lgyun.system.user.service;

import com.lgyun.common.enumeration.UserType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;

/**
 * 服务类
 *
 * @author tzq
 * @since 2020/6/6 22:09
 */
public interface IUserService extends BaseService<User> {

    /**
     * 用户信息
     *
     * @param userId
     * @param userType
     * @return
     */
    UserInfo queryUserInfoByUserId(Long userId, UserType userType);

    /**
     * 用户信息
     *
     * @param phone
     * @param userType
     * @return
     */
    UserInfo queryUserInfoByPhone(String phone, UserType userType);

    /**
     * 用户信息
     *
     * @param account
     * @param userType
     * @return
     */
    UserInfo queryUserInfoByAccount(String account, UserType userType);

}
