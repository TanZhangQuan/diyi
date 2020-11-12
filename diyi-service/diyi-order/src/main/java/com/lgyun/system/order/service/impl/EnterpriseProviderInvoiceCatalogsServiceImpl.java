package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.EnterpriseProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.mapper.EnterpriseProviderInvoiceCatalogsMapper;
import com.lgyun.system.order.service.IEnterpriseProviderInvoiceCatalogsService;
import com.lgyun.system.order.vo.EnterpriseProviderInvoiceCatalogsVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public R<IPage<EnterpriseProviderInvoiceCatalogsVO>> queryInvoiceCatalogsList(Long serviceProviderId, Long enterpriseId, IPage<EnterpriseProviderInvoiceCatalogsVO> page) {
        return R.data(page.setRecords(baseMapper.queryInvoiceCatalogsList(serviceProviderId, enterpriseId, page)));
    }
}
