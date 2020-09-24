package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.AdminEntity;

/**
 * 平台管理员信息表 Service 接口
 *
 * @author liangfeihu
 * @since 2020-09-19 15:02:12
 */
public interface IAdminService extends BaseService<AdminEntity> {

    /**
     * 查询当前管理员
     *
     * @param bladeUser
     * @return
     */
    R<AdminEntity> currentAdmin(BladeUser bladeUser);

    /**
     * 根据userId查询管理员
     *
     * @param userId
     * @return
     */
    AdminEntity findByUserId(Long userId);

    /**
     * 根据账号密码查询管理员是否存在
     *
     * @param userName
     * @param loginPwd
     * @return
     */
    Integer findCountByUserNameAndLoginPwd(String userName, String loginPwd);

    /**
     * 根据手机号查询管理员是否存在
     *
     * @param phoneNumber
     * @return
     */
    Integer findCountByPhoneNumber(String phoneNumber);

    /**
     * 根据手机号查询管理员
     *
     * @param phoneNumber
     * @return
     */
    AdminEntity findByPhoneNumber(String phoneNumber);

}

