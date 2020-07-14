package com.lgyun.system.user.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.EmployeeEntity;
import com.lgyun.system.user.mapper.EmployeeMapper;
import com.lgyun.system.user.service.IEmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Slf4j
@Service
@AllArgsConstructor
public class EmployeeServiceImpl extends BaseServiceImpl<EmployeeMapper, EmployeeEntity> implements IEmployeeService {

}
