package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.mapper.EnterpriseWorkerMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
import com.lgyun.system.user.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
@Slf4j
@Service
@AllArgsConstructor
public class EnterpriseWorkerServiceImpl extends BaseServiceImpl<EnterpriseWorkerMapper, EnterpriseWorkerEntity> implements IEnterpriseWorkerService {

    private IEnterpriseService enterpriseService;
    private IUserService userService;

    @Override
    public EnterpriseWorkerEntity findByPhoneNumber(String phoneNumber) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getPhoneNumber, phoneNumber);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public EnterpriseWorkerEntity findByEmployeeUserNameAndEmployeePwd(String employeeUserName, String employeePwd) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getEmployeeUserName, employeeUserName)
                .eq(EnterpriseWorkerEntity::getEmployeePwd, employeePwd);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<EnterpriseWorkerEntity> currentEnterpriseWorker(BladeUser bladeUser) {

        if (bladeUser == null || bladeUser.getUserId() == null) {
            return R.fail("账号未登陆");
        }

        User user = userService.getById(bladeUser.getUserId());
        if (user == null){
            return R.fail("用户不存在");
        }

        if (!(UserType.ENTERPRISE.equals(user.getUserType()))) {
            return R.fail("用户类型有误");
        }

        EnterpriseWorkerEntity enterpriseWorkerEntity = findByUserId(bladeUser.getUserId());
        if (enterpriseWorkerEntity == null) {
            return R.fail("账号未注册");
        }

        EnterpriseEntity enterpriseEntity = enterpriseService.getById(enterpriseWorkerEntity.getEnterpriseId());
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        if (!(AccountState.NORMAL.equals(enterpriseEntity.getEnterpriseState()))) {
            return R.fail("商户状态非正常，请联系客服");
        }

        if (!(AccountState.NORMAL.equals(enterpriseWorkerEntity.getEnterpriseWorkerState()))) {
            return R.fail("账号状态非正常，请联系客服");
        }

        return R.data(enterpriseWorkerEntity);
    }

    @Override
    public EnterpriseWorkerEntity findByUserId(Long userId) {
        QueryWrapper<EnterpriseWorkerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseWorkerEntity::getUserId, userId);
        return baseMapper.selectOne(queryWrapper);
    }
}
