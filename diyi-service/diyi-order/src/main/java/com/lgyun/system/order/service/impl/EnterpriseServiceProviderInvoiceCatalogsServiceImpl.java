package com.lgyun.system.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.mapper.EnterpriseServiceProviderInvoiceCatalogsMapper;
import com.lgyun.system.order.entity.EnterpriseServiceProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.service.IEnterpriseServiceProviderInvoiceCatalogsService;

/**
 * 商户-服务商开票类目：记录商户在特定服务商的开票类目 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
@Slf4j
@Service
@AllArgsConstructor
public class EnterpriseServiceProviderInvoiceCatalogsServiceImpl extends BaseServiceImpl<EnterpriseServiceProviderInvoiceCatalogsMapper, EnterpriseServiceProviderInvoiceCatalogsEntity> implements IEnterpriseServiceProviderInvoiceCatalogsService {

}
