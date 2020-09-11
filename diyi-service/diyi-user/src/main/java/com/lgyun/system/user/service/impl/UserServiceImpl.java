package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.CommonConstant;
import com.lgyun.common.constant.SmsConstant;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.*;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.feign.ISysClient;
import com.lgyun.system.user.dto.UpdatePasswordDto;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.excel.UserExcel;
import com.lgyun.system.user.mapper.UserMapper;
import com.lgyun.system.user.service.IUserService;
import lombok.AllArgsConstructor;
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
    private RedisUtil redisUtil;

    @Override
    public R<User> currentUser(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("未登陆状态");
        }

        User user = getById(bladeUser.getUserId());
        if (user == null){
            return R.fail("管理员不存在");
        }

        if (!(UserType.ADMIN.equals(user.getUserType()))) {
            return R.fail("用户类型有误");
        }

        if (!(AccountState.NORMAL.equals(user.getUserState()))) {
            return R.fail("管理员状态非正常，请联系客服");
        }

        return R.data(user);
    }

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
    public UserInfo userInfo(Long userId, UserType userType) {
        User user = baseMapper.selectById(userId);
        if (user == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        //查询角色别名
        if (!(UserType.MAKER.equals(userType))) {
            if (StringUtil.isNotBlank(user.getRoleId())) {
                List<String> roleAlias = baseMapper.getRoleAlias(Func.toStrArray(user.getRoleId()));
                userInfo.setRoles(roleAlias);
            }
        }

        return userInfo;
    }

    @Override
    public UserInfo userInfoByPhone(String phone, UserType userType) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getPhone, phone)
                .eq(User::getUserType, userType);

        User user = baseMapper.selectOne(queryWrapper);
        if (user == null) {
            return null;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        //查询角色别名
        if (!(UserType.MAKER.equals(userType))) {
            if (StringUtil.isNotBlank(user.getRoleId())) {
                List<String> roleAlias = baseMapper.getRoleAlias(Func.toStrArray(user.getRoleId()));
                userInfo.setRoles(roleAlias);
            }
        }

        return userInfo;
    }

    @Override
    public User findByPhone(String phone) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getPhone, phone)
                .eq(User::getUserType, UserType.ADMIN);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public UserInfo userInfo(String account, String password, UserType userType) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getAccount, account)
                .eq(User::getPassword, password)
                .eq(User::getUserType, userType);

        User user = baseMapper.selectOne(queryWrapper);
        if (user == null) {
            return null;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        //查询角色别名
        if (!(UserType.MAKER.equals(userType))) {
            if (StringUtil.isNotBlank(user.getRoleId())) {
                List<String> roleAlias = baseMapper.getRoleAlias(Func.toStrArray(user.getRoleId()));
                userInfo.setRoles(roleAlias);
            }
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
        return this.update(user, Wrappers.<User>update().lambda().in(User::getId, Func.toLongList(userIds)));
    }

    @Override
    public R<String> updatePassword(UpdatePasswordDto updatePasswordDto) {

        User user = findByPhone(updatePasswordDto.getPhoneNumber());
        if (user == null) {
            return R.fail("手机号未注册");
        }

        //查询缓存短信验证码
        String redisCode = (String) redisUtil.get(SmsConstant.AVAILABLE_TIME + updatePasswordDto.getPhoneNumber());
        //判断验证码
        if (!StringUtil.equalsIgnoreCase(redisCode, updatePasswordDto.getSmsCode())) {
            return R.fail("短信验证码不正确");
        }

        user.setPassword(DigestUtil.encrypt(updatePasswordDto.getNewPassword()));
        save(user);

        //删除缓存短信验证码
        redisUtil.del(SmsConstant.AVAILABLE_TIME + updatePasswordDto.getPhoneNumber());

        return R.success("修改密码成功");
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
