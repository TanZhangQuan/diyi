package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.entity.EnterpriseProviderInvoiceCatalogsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.vo.EnterpriseServiceProviderInvoiceCatalogsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商户-服务商开票类目：记录商户在特定服务商的开票类目 Mapper
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
@Mapper
public interface EnterpriseServiceProviderInvoiceCatalogsMapper extends BaseMapper<EnterpriseProviderInvoiceCatalogsEntity> {

    /**
     * 查询开票类目
     *
     * @param serviceProviderId
     * @param enterpriseId
     * @param page
     * @return
     */
    List<EnterpriseServiceProviderInvoiceCatalogsVO> queryInvoiceCatalogsList(Long serviceProviderId, Long enterpriseId, IPage<EnterpriseServiceProviderInvoiceCatalogsVO> page);
}

