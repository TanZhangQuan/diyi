package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.excel.UserExcel;

import java.util.List;

/**
 * 服务类
 *
 * @author liangfeihu
 * @since 2020/6/6 22:09
 */
public interface IUserService extends BaseService<User> {

    /**
     * 新增或修改用户
     *
     * @param user
     * @return
     */
    boolean submit(User user);

    /**
     * 用户信息
     *
     * @param userId
     * @param userType
     * @return
     */
    UserInfo userInfo(Long userId, UserType userType);

    /**
     * 用户信息
     *
     * @param phone
     * @param userType
     * @return
     */
    UserInfo userInfoByPhone(String phone, UserType userType);

    /**
     * 用户信息
     *
     * @param account
     * @param password
     * @param userType
     * @return
     */
    UserInfo userInfo(String account, String password, UserType userType);

    /**
     * 给用户设置角色
     *
     * @param userIds
     * @param roleIds
     * @return
     */
    boolean grant(String userIds, String roleIds);

    /**
     * 初始化密码
     *
     * @param userIds
     * @return
     */
    boolean resetPassword(String userIds);

    /**
     * 修改密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param newPassword1
     * @return
     */
    boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1);

    /**
     * 获取角色名
     *
     * @param roleIds
     * @return
     */
    List<String> getRoleName(String roleIds);

    /**
     * 获取部门名
     *
     * @param deptIds
     * @return
     */
    List<String> getDeptName(String deptIds);

    /**
     * 导入用户数据
     *
     * @param data
     */
    void importUser(List<UserExcel> data);

    /**
     * 获取导出用户数据
     *
     * @param queryWrapper
     * @return
     */
    List<UserExcel> exportUser(Wrapper<User> queryWrapper);

}
