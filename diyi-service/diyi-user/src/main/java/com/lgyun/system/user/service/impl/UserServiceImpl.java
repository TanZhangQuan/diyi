package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.entity.UserInfo;
import com.lgyun.system.user.mapper.UserMapper;
import com.lgyun.system.user.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author tzq
 * @since 2020/6/6 22:09
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public UserInfo queryUserInfoByUserId(Long userId, UserType userType) {
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
    public UserInfo queryUserInfoByPhone(String phone, UserType userType) {
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
    public UserInfo queryUserInfoByAccount(String account, UserType userType) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getAccount, account)
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

}
