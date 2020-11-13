package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.entity.ServiceProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.vo.ProviderInvoiceCatalogListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商户-服务商开票类目表 Mapper
 *
 * @author liangfeihu
 * @since 2020-11-12 17:54:16
 */
@Mapper
public interface ServiceProviderInvoiceCatalogsMapper extends BaseMapper<ServiceProviderInvoiceCatalogsEntity> {

    /**
     * 查询服务商所有开票类目
     *
     * @param serviceProviderId
     * @param page
     * @return
     */
    List<ProviderInvoiceCatalogListVO> queryInvoiceCatalogList(Long serviceProviderId, IPage<ProviderInvoiceCatalogListVO> page);

    /**
     * 查询编辑开票类目详情
     *
     * @param invoiceCatalogId
     * @return
     */
    ProviderInvoiceCatalogListVO queryInvoiceCatalogUpdateDetail(Long invoiceCatalogId);
}

