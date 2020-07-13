package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.tool.*;
import lombok.AllArgsConstructor;
import com.lgyun.common.constant.CommonConstant;
import com.lgyun.common.exception.ServiceException;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.feign.ISysClient;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.excel.UserExcel;
import com.lgyun.system.user.mapper.UserMapper;
import com.lgyun.system.user.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 服务实现类
 *
 * @author liangfeihu
 * @since 2020/6/6 22:09
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    private ISysClient sysClient;

    @Override
    public boolean submit(User user) {

        if (UserType.MAKER.equals(user.getUserType())) {
            throw new ApiException("用户类型不允许手动添加修改");
        }

        if (Func.isNoneBlank(user.getPassword())) {
            user.setPassword(DigestUtil.encrypt(user.getPassword()));
        }
        Integer cnt = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getTenantId, user.getTenantId()).eq(User::getUserType, user.getUserType()).eq(User::getAccount, user.getAccount()));
        if (cnt > 0) {
            throw new ApiException("当前用户已存在!");
        }
        return saveOrUpdate(user);
    }

    @Override
    public IPage<User> selectUserPage(IPage<User> page, User user) {
        return page.setRecords(baseMapper.selectUserPage(page, user));
    }

    @Override
    public UserInfo userInfo(Long userId, UserType userType) {
        User user = baseMapper.selectById(userId);
        if (user == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        //获取角色别名
        if (!(UserType.MAKER.equals(userType))){
            List<String> roleAlias = baseMapper.getRoleAlias(Func.toStrArray(user.getRoleId()));
            userInfo.setRoles(roleAlias);
        }

        return userInfo;
    }

    @Override
    public UserInfo userInfoByPhone(String phone, UserType userType) {
        User user = baseMapper.getUserByPhone(phone, userType);
        if (user == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        //获取角色别名
        if (!(UserType.MAKER.equals(userType))){
            List<String> roleAlias = baseMapper.getRoleAlias(Func.toStrArray(user.getRoleId()));
            userInfo.setRoles(roleAlias);
        }

        return userInfo;
    }

    @Override
    public UserInfo userInfo(String account, String password, UserType userType) {
        User user = baseMapper.getUser(account, password, userType);
        if (user == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        //获取角色别名
        if (!(UserType.MAKER.equals(userType))){
            List<String> roleAlias = baseMapper.getRoleAlias(Func.toStrArray(user.getRoleId()));
            userInfo.setRoles(roleAlias);
        }
        return userInfo;
    }

    @Override
    public boolean grant(String userIds, String roleIds) {
        User user = new User();
        user.setRoleId(roleIds);
        return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
    }

    @Override
    public boolean resetPassword(String userIds) {
        User user = new User();
        user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
        user.setUpdateTime(DateUtil.now());
        return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
    }

    @Override
    public boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1) {
        User user = getById(userId);
        if (!newPassword.equals(newPassword1)) {
            throw new ServiceException("请输入正确的确认密码!");
        }
        if (!user.getPassword().equals(DigestUtil.encrypt(oldPassword))) {
            throw new ServiceException("原密码不正确!");
        }
        return this.update(Wrappers.<User>update().lambda().set(User::getPassword, DigestUtil.encrypt(newPassword)).eq(User::getId, userId));
    }

    @Override
    public List<String> getRoleName(String roleIds) {
        return baseMapper.getRoleName(Func.toStrArray(roleIds));
    }

    @Override
    public List<String> getDeptName(String deptIds) {
        return baseMapper.getDeptName(Func.toStrArray(deptIds));
    }

    @Override
    public void importUser(List<UserExcel> data) {
        data.forEach(userExcel -> {
            User user = Objects.requireNonNull(BeanUtil.copy(userExcel, User.class));
            // 设置部门ID
            user.setDeptId(sysClient.getDeptIds(userExcel.getTenantId(), userExcel.getDeptName()));
            // 设置岗位ID
            user.setPostId(sysClient.getPostIds(userExcel.getTenantId(), userExcel.getPostName()));
            // 设置角色ID
            user.setRoleId(sysClient.getRoleIds(userExcel.getTenantId(), userExcel.getRoleName()));
            // 设置默认密码
            user.setPassword(DigestUtil.encrypt(CommonConstant.DEFAULT_PASSWORD));
            this.submit(user);
        });
    }

    @Override
    public List<UserExcel> exportUser(Wrapper<User> queryWrapper) {
        List<UserExcel> userList = baseMapper.exportUser(queryWrapper);
        userList.forEach(user -> {
            user.setRoleName(StringUtil.join(sysClient.getRoleNames(user.getRoleId())));
            user.setDeptName(StringUtil.join(sysClient.getDeptNames(user.getDeptId())));
            user.setPostName(StringUtil.join(sysClient.getPostNames(user.getPostId())));
        });
        return userList;
    }

}
