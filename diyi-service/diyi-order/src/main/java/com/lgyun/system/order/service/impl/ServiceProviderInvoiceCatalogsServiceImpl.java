package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.AddOrUpdateProviderInvoiceCatalogDTO;
import com.lgyun.system.order.entity.ServiceProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.mapper.ServiceProviderInvoiceCatalogsMapper;
import com.lgyun.system.order.service.IServiceProviderInvoiceCatalogsService;
import com.lgyun.system.order.vo.ProviderInvoiceCatalogListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 服务商开票类目表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-11-12 17:54:16
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderInvoiceCatalogsServiceImpl extends BaseServiceImpl<ServiceProviderInvoiceCatalogsMapper, ServiceProviderInvoiceCatalogsEntity> implements IServiceProviderInvoiceCatalogsService {

    @Override
    public R<IPage<ProviderInvoiceCatalogListVO>> queryInvoiceCatalogList(Long serviceProviderId, IPage<ProviderInvoiceCatalogListVO> page) {
        return R.data(page.setRecords(baseMapper.queryInvoiceCatalogList(serviceProviderId, page)));
    }

    @Override
    public R<ProviderInvoiceCatalogListVO> queryInvoiceCatalogUpdateDetail(Long invoiceCatalogId) {
        return R.data(baseMapper.queryInvoiceCatalogUpdateDetail(invoiceCatalogId));
    }

    @Override
    public R<String> addOrUpdateInvoiceCatalog(AddOrUpdateProviderInvoiceCatalogDTO addOrUpdateProviderInvoiceCatalogDTO, Long serviceProviderId) {

        ServiceProviderInvoiceCatalogsEntity serviceProviderInvoiceCatalogsEntity;
        if (addOrUpdateProviderInvoiceCatalogDTO.getInvoiceCatalogId() != null) {
            serviceProviderInvoiceCatalogsEntity = getById(addOrUpdateProviderInvoiceCatalogDTO.getInvoiceCatalogId());
            if (serviceProviderInvoiceCatalogsEntity == null) {
                return R.fail("服务商开票类目不存在");
            }

            if (!(serviceProviderInvoiceCatalogsEntity.getServiceProviderId().equals(serviceProviderId))) {
                return R.fail("服务商开票类目不属于服务商");
            }

            BeanUtils.copyProperties(addOrUpdateProviderInvoiceCatalogDTO, serviceProviderInvoiceCatalogsEntity);
            updateById(serviceProviderInvoiceCatalogsEntity);

            return R.success("编辑服务商开票类目成功");
        } else {
            serviceProviderInvoiceCatalogsEntity = new ServiceProviderInvoiceCatalogsEntity();
            serviceProviderInvoiceCatalogsEntity.setServiceProviderId(serviceProviderId);

            BeanUtils.copyProperties(addOrUpdateProviderInvoiceCatalogDTO, serviceProviderInvoiceCatalogsEntity);
            save(serviceProviderInvoiceCatalogsEntity);

            return R.success("新建服务商开票类目成功");
        }

    }
}
