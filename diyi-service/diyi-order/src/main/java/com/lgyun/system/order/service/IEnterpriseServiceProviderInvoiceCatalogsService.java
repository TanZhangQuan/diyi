package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.EnterpriseServiceProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.vo.EnterpriseServiceProviderInvoiceCatalogsVO;

/**
 * 商户-服务商开票类目：记录商户在特定服务商的开票类目 Service 接口
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
public interface IEnterpriseServiceProviderInvoiceCatalogsService extends BaseService<EnterpriseServiceProviderInvoiceCatalogsEntity> {

    /**
     * 查询开票类目
     *
     * @param page
     * @return
     */
    R<IPage<EnterpriseServiceProviderInvoiceCatalogsVO>> queryInvoiceCatalogsList(Long serviceProviderId, Long enterpriseId, IPage<EnterpriseServiceProviderInvoiceCatalogsVO> page);
}

