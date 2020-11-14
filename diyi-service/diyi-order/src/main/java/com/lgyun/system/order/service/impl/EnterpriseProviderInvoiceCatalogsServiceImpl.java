package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.SetType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.AddOrUpdateEnterpriseProviderInvoiceCatalogDTO;
import com.lgyun.system.order.entity.EnterpriseProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.mapper.EnterpriseProviderInvoiceCatalogsMapper;
import com.lgyun.system.order.service.IEnterpriseProviderInvoiceCatalogsService;
import com.lgyun.system.order.vo.EnterpriseProviderInvoiceCatalogListVO;
import com.lgyun.system.order.vo.EnterpriseProviderInvoiceCatalogsVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    @Override
    public R<IPage<EnterpriseProviderInvoiceCatalogListVO>> queryEnterpriseProviderInvoiceCatalogList(Long serviceProviderId, Long enterpriseId, IPage<EnterpriseProviderInvoiceCatalogListVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseProviderInvoiceCatalogList(serviceProviderId, enterpriseId, page)));
    }

    @Override
    public R<EnterpriseProviderInvoiceCatalogListVO> queryEnterpriseProviderInvoiceCatalogUpdateDetail(Long enterpriseProviderInvoiceCatalogId) {
        return R.data(baseMapper.queryEnterpriseProviderInvoiceCatalogUpdateDetail(enterpriseProviderInvoiceCatalogId));
    }

    @Override
    public R<String> addOrUpdateEnterpriseProviderInvoiceCatalog(AddOrUpdateEnterpriseProviderInvoiceCatalogDTO addOrUpdateEnterpriseProviderInvoiceCatalogDTO, Long serviceProviderId, Long enterpriseId, String name) {

        EnterpriseProviderInvoiceCatalogsEntity enterpriseProviderInvoiceCatalogsEntity;
        if (addOrUpdateEnterpriseProviderInvoiceCatalogDTO.getEnterpriseProviderInvoiceCatalogId() != null) {
            enterpriseProviderInvoiceCatalogsEntity = getById(addOrUpdateEnterpriseProviderInvoiceCatalogDTO.getEnterpriseProviderInvoiceCatalogId());
            if (enterpriseProviderInvoiceCatalogsEntity == null) {
                return R.fail("商户-服务商开票类目不存在");
            }

            if (!(enterpriseProviderInvoiceCatalogsEntity.getServiceProviderId().equals(serviceProviderId))) {
                return R.fail("商户-服务商开票类目不属于服务商");
            }

            if (!(enterpriseProviderInvoiceCatalogsEntity.getEnterpriseId().equals(enterpriseId))) {
                return R.fail("商户-服务商开票类目不属于商户");
            }

            enterpriseProviderInvoiceCatalogsEntity.setSetPerson(name);
            BeanUtils.copyProperties(addOrUpdateEnterpriseProviderInvoiceCatalogDTO, enterpriseProviderInvoiceCatalogsEntity);
            updateById(enterpriseProviderInvoiceCatalogsEntity);

            return R.success("编辑商户-服务商开票类目成功");

        } else {

            enterpriseProviderInvoiceCatalogsEntity = new EnterpriseProviderInvoiceCatalogsEntity();
            enterpriseProviderInvoiceCatalogsEntity.setServiceProviderId(serviceProviderId);
            enterpriseProviderInvoiceCatalogsEntity.setEnterpriseId(enterpriseId);
            enterpriseProviderInvoiceCatalogsEntity.setSetPerson(name);
            enterpriseProviderInvoiceCatalogsEntity.setSetType(SetType.MANUAL);
            BeanUtils.copyProperties(addOrUpdateEnterpriseProviderInvoiceCatalogDTO, enterpriseProviderInvoiceCatalogsEntity);
            save(enterpriseProviderInvoiceCatalogsEntity);

            return R.success("新建商户-服务商开票类目成功");
        }

    }
}
