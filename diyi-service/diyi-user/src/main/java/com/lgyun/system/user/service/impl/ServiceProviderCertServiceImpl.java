package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.admin.AddOrUpdateServiceProviderCertDTO;
import com.lgyun.system.user.entity.ServiceProviderCertEntity;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.mapper.ServiceProviderCertMapper;
import com.lgyun.system.user.service.IServiceProviderCertService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.vo.admin.ServiceProviderCertListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 服务商资格信息表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-09-17 10:58:41
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderCertServiceImpl extends BaseServiceImpl<ServiceProviderCertMapper, ServiceProviderCertEntity> implements IServiceProviderCertService {

    private IServiceProviderService serviceProviderService;

    @Override
    public R<IPage<ServiceProviderCertListVO>> queryServiceProviderCertList(Long serviceProviderId, IPage<ServiceProviderCertListVO> page) {
        return R.data(page.setRecords(baseMapper.queryServiceProviderCertList(serviceProviderId, page)));
    }

    @Override
    public R<String> addOrUpdateServiceProviderCert(AddOrUpdateServiceProviderCertDTO addOrUpdateServiceProviderCertDTO) {

        ServiceProviderCertEntity serviceProviderCertEntity;
        if (addOrUpdateServiceProviderCertDTO.getServiceProviderCertId() != null) {

            serviceProviderCertEntity = getById(addOrUpdateServiceProviderCertDTO.getServiceProviderCertId());
            if (serviceProviderCertEntity == null) {
                return R.fail("服务商资格信息不存在");
            }

            serviceProviderCertEntity.setCertificateType(addOrUpdateServiceProviderCertDTO.getCertificateType());
            serviceProviderCertEntity.setCertificateName(addOrUpdateServiceProviderCertDTO.getCertificateName());
            serviceProviderCertEntity.setCertificateDesc(addOrUpdateServiceProviderCertDTO.getCertificateDesc());
            serviceProviderCertEntity.setCertificateMainUrl(addOrUpdateServiceProviderCertDTO.getCertificateMainUrl());
            updateById(serviceProviderCertEntity);

        } else {

            if (addOrUpdateServiceProviderCertDTO.getServiceProviderId() == null) {
                return R.fail("请输入服务商编号");
            }

            ServiceProviderEntity serviceProviderEntity = serviceProviderService.getById(addOrUpdateServiceProviderCertDTO.getServiceProviderId());
            if (serviceProviderEntity == null) {
                return R.fail("服务商不存在");
            }

            serviceProviderCertEntity = new ServiceProviderCertEntity();
            serviceProviderCertEntity.setServiceProviderId(serviceProviderEntity.getId());
            serviceProviderCertEntity.setCertificateType(addOrUpdateServiceProviderCertDTO.getCertificateType());
            serviceProviderCertEntity.setCertificateName(addOrUpdateServiceProviderCertDTO.getCertificateName());
            serviceProviderCertEntity.setCertificateDesc(addOrUpdateServiceProviderCertDTO.getCertificateDesc());
            serviceProviderCertEntity.setCertificateMainUrl(addOrUpdateServiceProviderCertDTO.getCertificateMainUrl());
            save(serviceProviderCertEntity);
        }

        return R.success("操作成功");
    }
}
