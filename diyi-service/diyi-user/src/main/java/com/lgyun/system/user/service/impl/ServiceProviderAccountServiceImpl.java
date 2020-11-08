package com.lgyun.system.user.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.ServiceProviderAccountEntity;
import com.lgyun.system.user.mapper.ServiceProviderAccountMapper;
import com.lgyun.system.user.service.IServiceProviderAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 服务商收款账户信息表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-11-08 20:57:37
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderAccountServiceImpl extends BaseServiceImpl<ServiceProviderAccountMapper, ServiceProviderAccountEntity> implements IServiceProviderAccountService {

}
