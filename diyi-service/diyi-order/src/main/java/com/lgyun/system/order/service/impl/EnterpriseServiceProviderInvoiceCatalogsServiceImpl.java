package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.system.order.vo.EnterpriseServiceProviderInvoiceCatalogsVO;
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

    @Override
    public R<IPage<EnterpriseServiceProviderInvoiceCatalogsVO>> queryInvoiceCatalogsList(Long serviceProviderId, Long enterpriseId, IPage<EnterpriseServiceProviderInvoiceCatalogsVO> page) {
        return R.data(page.setRecords(baseMapper.queryInvoiceCatalogsList(serviceProviderId, enterpriseId, page)));
    }
}
