package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.EnterpriseWorkerEntity;
import com.lgyun.system.user.mapper.EnterpriseWorkerMapper;
import com.lgyun.system.user.service.IEnterpriseWorkerService;
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
}
