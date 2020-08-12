package com.lgyun.system.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.EnterpriseProviderInvoiceCatalogsMapper;
import com.lgyun.system.order.entity.EnterpriseProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.service.IEnterpriseProviderInvoiceCatalogsService;

/**
 * 商户-服务商开票类目：记录商户在特定服务商的开票类目 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
@Slf4j
@Service
@AllArgsConstructor
public class EnterpriseProviderInvoiceCatalogsServiceImpl extends BaseServiceImpl<EnterpriseProviderInvoiceCatalogsMapper, EnterpriseProviderInvoiceCatalogsEntity> implements IEnterpriseProviderInvoiceCatalogsService {

}
