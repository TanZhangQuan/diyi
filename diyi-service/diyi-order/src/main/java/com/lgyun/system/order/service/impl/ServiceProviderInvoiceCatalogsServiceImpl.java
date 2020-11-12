package com.lgyun.system.order.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.ServiceProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.mapper.ServiceProviderInvoiceCatalogsMapper;
import com.lgyun.system.order.service.IServiceProviderInvoiceCatalogsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 商户-服务商开票类目表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-11-12 17:54:16
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderInvoiceCatalogsServiceImpl extends BaseServiceImpl<ServiceProviderInvoiceCatalogsMapper, ServiceProviderInvoiceCatalogsEntity> implements IServiceProviderInvoiceCatalogsService {

}
