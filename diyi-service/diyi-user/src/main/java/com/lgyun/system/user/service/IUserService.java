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
 * @author tzq
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
    UserInfo userInfoFindByUserIdAndUserType(Long userId, UserType userType);

    /**
     * 用户信息
     *
     * @param phone
     * @param userType
     * @return
     */
    UserInfo userInfoFindByPhoneAndUserType(String phone, UserType userType);

    /**
     * 用户信息
     *
     * @param account
     * @param userType
     * @return
     */
    UserInfo userInfoByAccountAndUserType(String account, UserType userType);

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
     * 查询角色名
     *
     * @param roleIds
     * @return
     */
    List<String> getRoleName(String roleIds);

    /**
     * 查询部门名
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
     * 查询导出用户数据
     *
     * @param queryWrapper
     * @return
     */
    List<UserExcel> exportUser(Wrapper<User> queryWrapper);

}
