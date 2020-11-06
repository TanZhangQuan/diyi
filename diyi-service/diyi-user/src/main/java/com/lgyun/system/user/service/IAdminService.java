package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.vo.AdminDetailVO;
import com.lgyun.system.user.vo.AdminVO;

import java.util.List;

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
     * 查询当前管理员详情
     *
     * @param adminId
     * @return
     */
    R<AdminDetailVO> queryAdminDetail(Long adminId);

    /**
     * 根据userId查询管理员
     *
     * @param userId
     * @return
     */
    AdminEntity findByUserId(Long userId);

    /**
     * 根据手机号查询管理员是否存在
     *
     * @param phoneNumber
     * @return
     */
    int findCountByPhoneNumber(String phoneNumber);

    /**
     * 根据手机号查询管理员
     *
     * @param phoneNumber
     * @return
     */
    AdminEntity findByPhoneNumber(String phoneNumber);

    /**
     * 根据账号密码查询管理员
     *
     * @param userName
     * @param loginPwd
     * @return
     */
    AdminEntity findByUserNameAndLoginPwd(String userName, String loginPwd);

    /**
     * 查询当前管理员的所有主子账号
     *
     * @param id
     * @return
     */
    R<List<AdminVO>> queryChildAccountList(Long id);

    /**
     * 查询管理员账号详情
     *
     * @param accountId
     * @return
     */
    R<AdminVO> queryAccountDetail(Long accountId);
}

