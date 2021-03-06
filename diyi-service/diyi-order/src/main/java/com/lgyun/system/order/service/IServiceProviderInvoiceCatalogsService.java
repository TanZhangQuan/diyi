package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ApplyScope;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.AddOrUpdateProviderInvoiceCatalogDTO;
import com.lgyun.system.order.entity.ServiceProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.vo.ProviderInvoiceCatalogListVO;

/**
 * 商户-服务商开票类目表 Service 接口
 *
 * @author tzq
 * @since 2020-11-12 17:54:16
 */
public interface IServiceProviderInvoiceCatalogsService extends BaseService<ServiceProviderInvoiceCatalogsEntity> {

    /**
     * 查询服务商所有开票类目
     *
     * @param serviceProviderId
     * @param page
     * @return
     */
    R<IPage<ProviderInvoiceCatalogListVO>> queryInvoiceCatalogList(Long serviceProviderId, IPage<ProviderInvoiceCatalogListVO> page);

    /**
     * 查询编辑开票类目详情
     *
     * @param invoiceCatalogId
     * @return
     */
    R<ProviderInvoiceCatalogListVO> queryInvoiceCatalogUpdateDetail(Long invoiceCatalogId);

    /**
     * 添加/编辑开票类目
     *
     * @param addOrUpdateProviderInvoiceCatalogDTO
     * @param serviceProviderId
     * @return
     */
    R<String> addOrUpdateInvoiceCatalog(AddOrUpdateProviderInvoiceCatalogDTO addOrUpdateProviderInvoiceCatalogDTO, Long serviceProviderId);

    /**
     * 查询服务商开票范围内所有开票类目名称
     *
     * @param serviceProviderId
     * @return
     */
    R<String> queryProviderInvoiceCatalogNameList(Long serviceProviderId, ApplyScope applyScope);

}

