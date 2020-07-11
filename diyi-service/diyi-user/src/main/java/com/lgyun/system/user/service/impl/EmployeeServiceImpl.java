package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.EmployeeEntity;
import com.lgyun.system.user.mapper.EmployeeMapper;
import com.lgyun.system.user.service.IEmployeeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Service
@AllArgsConstructor
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeeEntity> implements IEmployeeService {
    private static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

}
