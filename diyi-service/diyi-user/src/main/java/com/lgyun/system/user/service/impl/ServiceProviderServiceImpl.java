package com.lgyun.system.user.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.mapper.ServiceProviderMapper;
import com.lgyun.system.user.service.IServiceProviderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderServiceImpl extends BaseServiceImpl<ServiceProviderMapper, ServiceProviderEntity> implements IServiceProviderService {

}
