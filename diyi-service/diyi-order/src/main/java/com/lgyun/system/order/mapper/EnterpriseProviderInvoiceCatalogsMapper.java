package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.entity.EnterpriseProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.vo.EnterpriseProviderInvoiceCatalogListVO;
import com.lgyun.system.order.vo.EnterpriseProviderInvoiceCatalogsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商户-服务商开票类目：记录商户在特定服务商的开票类目 Mapper
 *
 * @author tzq
 * @since 2020-08-11 16:00:00
 */
@Mapper
public interface EnterpriseProviderInvoiceCatalogsMapper extends BaseMapper<EnterpriseProviderInvoiceCatalogsEntity> {

    /**
     * 查询开票类目
     *
     * @param serviceProviderId
     * @param enterpriseId
     * @param page
     * @return
     */
    List<EnterpriseProviderInvoiceCatalogsVO> queryInvoiceCatalogsList(Long serviceProviderId, Long enterpriseId, IPage<EnterpriseProviderInvoiceCatalogsVO> page);

    /**
     * 查询商户-服务商所有开票类目
     *
     * @param serviceProviderId
     * @param enterpriseId
     * @param page
     * @return
     */
    List<EnterpriseProviderInvoiceCatalogListVO> queryEnterpriseProviderInvoiceCatalogList(Long serviceProviderId, Long enterpriseId, IPage<EnterpriseProviderInvoiceCatalogListVO> page);

    /**
     * 查询编辑商户-服务商开票类目详情
     *
     * @param enterpriseProviderInvoiceCatalogId
     * @return
     */
    EnterpriseProviderInvoiceCatalogListVO queryEnterpriseProviderInvoiceCatalogUpdateDetail(Long enterpriseProviderInvoiceCatalogId);
}

