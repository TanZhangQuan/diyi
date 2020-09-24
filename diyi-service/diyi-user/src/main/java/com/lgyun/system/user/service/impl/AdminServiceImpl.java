package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.RedisUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.mapper.AdminMapper;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 平台管理员信息表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-09-19 15:02:12
 */
@Slf4j
@Service
@AllArgsConstructor
public class AdminServiceImpl extends BaseServiceImpl<AdminMapper, AdminEntity> implements IAdminService {

    private IUserService userService;
    private RedisUtil redisUtil;

    @Override
    public R<AdminEntity> currentAdmin(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("账号未登录");
        }

        User user = userService.getById(bladeUser.getUserId());
        if (user == null) {
            return R.fail("用户不存在");
        }

        if (!(UserType.ADMIN.equals(user.getUserType()))) {
            return R.fail("用户类型有误");
        }

        AdminEntity adminEntity = findByUserId(bladeUser.getUserId());
        if (adminEntity == null) {
            return R.fail("账号未注册");
        }

        if (!(AccountState.NORMAL.equals(adminEntity.getAdminState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        return R.data(adminEntity);
    }

    @Override
    public AdminEntity findByUserId(Long userId) {
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminEntity::getUserId, userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer findCountByUserNameAndLoginPwd(String userName, String loginPwd) {
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminEntity::getUserName, userName)
                .eq(AdminEntity::getLoginPwd, loginPwd);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer findCountByPhoneNumber(String phoneNumber) {
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public AdminEntity findByPhoneNumber(String phoneNumber) {
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectOne(queryWrapper);
    }

}
