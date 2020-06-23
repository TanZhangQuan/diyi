package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
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
        if (Func.isNotEmpty(user.getPassword())) {
            user.setPassword(DigestUtil.encrypt(user.getPassword()));
        }
        Integer cnt = baseMapper.selectCount(Wrappers.<User>query().lambda().eq(User::getTenantId, user.getTenantId()).eq(User::getAccount, user.getAccount()));
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
    public UserInfo userInfo(Long userId) {
        UserInfo userInfo = new UserInfo();
        User user = baseMapper.selectById(userId);
        userInfo.setUser(user);
        if (Func.isNotEmpty(user)) {
            List<String> roleAlias = baseMapper.getRoleAlias(Func.toStrArray(user.getRoleId()));
            userInfo.setRoles(roleAlias);
        }
        return userInfo;
    }

    @Override
    public UserInfo userInfo(String tenantId, String account, String password) {
        UserInfo userInfo = new UserInfo();
        User user = baseMapper.getUser(tenantId, account, password);
        userInfo.setUser(user);
        if (Func.isNotEmpty(user)) {
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
