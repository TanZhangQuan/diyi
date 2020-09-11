package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.ServiceProviderBankCardDto;
import com.lgyun.system.user.dto.ServiceProviderContactPersonDto;
import com.lgyun.system.user.dto.ServiceProviderInvoiceDto;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.mapper.ServiceProviderMapper;
import com.lgyun.system.user.service.IEnterpriseProviderService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author tzq
 * @since 2020-07-25 14:38:06
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderServiceImpl extends BaseServiceImpl<ServiceProviderMapper, ServiceProviderEntity> implements IServiceProviderService {

    private IEnterpriseProviderService enterpriseProviderService;

    @Override
    public R<IPage<EnterprisesVO>> getEnterpriseByServiceProvider(Query query, Long serviceProviderId, String keyword) {
        return enterpriseProviderService.getEnterpriseByServiceProvider(Condition.getPage(query.setDescs("create_time")), serviceProviderId, keyword);
    }

    @Override
    public R<ServiceProviderBankCardVO> getBankCard(Long serviceProviderId) {
        return R.data(baseMapper.getBankCard(serviceProviderId));
    }

    @Override
    public R<String> addOrUpdateBankCard(ServiceProviderBankCardDto serviceProviderBankCardDto, Long serviceProviderId) {

        ServiceProviderEntity serviceProviderWorkerEntity = getById(serviceProviderId);
        if (serviceProviderWorkerEntity == null){
            return R.fail("服务商不存在");
        }

        BeanUtils.copyProperties(serviceProviderBankCardDto, serviceProviderWorkerEntity);
        updateById(serviceProviderWorkerEntity);

        return R.success("操作成功");
    }

    @Override
    public R<ServiceProviderContactPersonVO> getContactPerson(Long serviceProviderId) {
        return R.data(baseMapper.getContactPerson(serviceProviderId));
    }

    @Override
    public R<String> addOrUpdateContactPerson(ServiceProviderContactPersonDto serviceProviderContactPersonDto, Long serviceProviderId) {

        ServiceProviderEntity serviceProviderWorkerEntity = getById(serviceProviderId);
        if (serviceProviderWorkerEntity == null){
            return R.fail("服务商不存在");
        }

        BeanUtils.copyProperties(serviceProviderContactPersonDto, serviceProviderWorkerEntity);
        updateById(serviceProviderWorkerEntity);

        return R.success("操作成功");
    }

    @Override
    public R<ServiceProviderInvoiceVO> getInvoice(Long serviceProviderId) {
        return R.data(baseMapper.getInvoice(serviceProviderId));
    }

    @Override
    public R<String> addOrUpdateInvoice(ServiceProviderInvoiceDto serviceProviderInvoiceDto, Long serviceProviderId) {

        ServiceProviderEntity serviceProviderWorkerEntity = getById(serviceProviderId);
        if (serviceProviderWorkerEntity == null){
            return R.fail("服务商不存在");
        }

        BeanUtils.copyProperties(serviceProviderInvoiceDto, serviceProviderWorkerEntity);
        updateById(serviceProviderWorkerEntity);

        return R.success("操作成功");
    }

}
