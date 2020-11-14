package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.AddOrUpdateEnterpriseProviderInvoiceCatalogDTO;
import com.lgyun.system.order.entity.EnterpriseProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.vo.EnterpriseProviderInvoiceCatalogListVO;
import com.lgyun.system.order.vo.EnterpriseProviderInvoiceCatalogsVO;

/**
 * 商户-服务商开票类目：记录商户在特定服务商的开票类目 Service 接口
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
public interface IEnterpriseProviderInvoiceCatalogsService extends BaseService<EnterpriseProviderInvoiceCatalogsEntity> {

    /**
     * 查询开票类目
     *
     * @param page
     * @return
     */
    R<IPage<EnterpriseProviderInvoiceCatalogsVO>> queryInvoiceCatalogsList(Long serviceProviderId, Long enterpriseId, IPage<EnterpriseProviderInvoiceCatalogsVO> page);

    /**
     * 查询商户-服务商所有开票类目
     *
     * @param serviceProviderId
     * @param enterpriseId
     * @param page
     * @return
     */
    R<IPage<EnterpriseProviderInvoiceCatalogListVO>> queryEnterpriseProviderInvoiceCatalogList(Long serviceProviderId, Long enterpriseId, IPage<EnterpriseProviderInvoiceCatalogListVO> page);

    /**
     * 查询编辑商户-服务商开票类目详情
     *
     * @param enterpriseProviderInvoiceCatalogId
     * @return
     */
    R<EnterpriseProviderInvoiceCatalogListVO> queryEnterpriseProviderInvoiceCatalogUpdateDetail(Long enterpriseProviderInvoiceCatalogId);

    /**
     * 添加/编辑商户-服务商开票类目
     *
     * @param addOrUpdateEnterpriseProviderInvoiceCatalogDTO
     * @param serviceProviderId
     * @param enterpriseId
     * @param name
     * @return
     */
    R<String> addOrUpdateEnterpriseProviderInvoiceCatalog(AddOrUpdateEnterpriseProviderInvoiceCatalogDTO addOrUpdateEnterpriseProviderInvoiceCatalogDTO, Long serviceProviderId, Long enterpriseId, String name);
}

