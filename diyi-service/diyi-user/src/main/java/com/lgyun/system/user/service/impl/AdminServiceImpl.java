package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.mapper.AdminMapper;
import com.lgyun.system.user.service.IAdminService;
import com.lgyun.system.user.vo.AdminDetailVO;
import com.lgyun.system.user.vo.AdminVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public R<AdminEntity> currentAdmin(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("用户未登录");
        }

        AdminEntity adminEntity = findByUserId(bladeUser.getUserId());
        if (adminEntity == null) {
            return R.fail("管理员不存在");
        }

        if (!(AccountState.NORMAL.equals(adminEntity.getAdminState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        return R.data(adminEntity);
    }

    @Override
    public R<AdminDetailVO> queryAdminDetail(Long adminId) {
        return R.data(baseMapper.queryAdminDetail(adminId));
    }

    @Override
    public AdminEntity findByUserId(Long userId) {
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminEntity::getUserId, userId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int findCountByPhoneNumber(String phoneNumber) {
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

    @Override
    public AdminEntity findByUserNameAndLoginPwd(String userName, String loginPwd) {
        QueryWrapper<AdminEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminEntity::getUserName, userName)
                .eq(AdminEntity::getLoginPwd, loginPwd);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<List<AdminVO>> queryChildAccountList(Long id) {
        AdminEntity adminEntity = this.getById(id);
        List<AdminEntity> list = null;
        if (adminEntity.getUpLevelId() != null) {
            list = this.list(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getId, adminEntity.getUpLevelId()).or().eq(AdminEntity::getUpLevelId, adminEntity.getUpLevelId()));
        } else {
            list = this.list(new QueryWrapper<AdminEntity>().lambda().eq(AdminEntity::getUpLevelId, adminEntity.getUpLevelId()));
            list.add(adminEntity);
        }
        List<AdminVO> adminVOS = new ArrayList<>();
        for (AdminEntity admin : list) {
            AdminVO adminVO = new AdminVO();
            BeanUtil.copyProperties(admin, adminVO);
            adminVO.setPositionName(admin.getPositionName().getDesc());
            adminVO.setAdminState(admin.getAdminState().getDesc());
            adminVOS.add(adminVO);
        }
        return R.data(adminVOS);
    }

    @Override
    public R<AdminVO> queryAccountDetail(Long accountId) {
        AdminEntity adminEntity = this.getById(accountId);
//        BeanUtil.copyProperties()
        return null;
    }
}
