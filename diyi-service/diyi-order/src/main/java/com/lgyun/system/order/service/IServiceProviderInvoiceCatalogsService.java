package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.AddOrUpdateProviderInvoiceCatalogDTO;
import com.lgyun.system.order.entity.ServiceProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.vo.ProviderInvoiceCatalogListVO;
import com.lgyun.system.order.vo.ProviderInvoiceCatalogUpdateDetailVO;

/**
 * 商户-服务商开票类目表 Service 接口
 *
 * @author liangfeihu
 * @since 2020-11-12 17:54:16
 */
public interface IServiceProviderInvoiceCatalogsService extends BaseService<ServiceProviderInvoiceCatalogsEntity> {

    R<IPage<ProviderInvoiceCatalogListVO>> queryInvoiceCatalogList(Long serviceProviderId, IPage<ProviderInvoiceCatalogListVO> page);

    R<ProviderInvoiceCatalogUpdateDetailVO> queryInvoiceCatalogUpdateDetail(Long invoiceCatalogId);

    R<String> addOrUpdateInvoiceCatalog(AddOrUpdateProviderInvoiceCatalogDTO addOrUpdateProviderInvoiceCatalogDTO, Long serviceProviderId);

}

