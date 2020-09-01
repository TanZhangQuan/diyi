package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.ServiceProviderBankCardDto;
import com.lgyun.system.user.dto.ServiceProviderContactPersonDto;
import com.lgyun.system.user.dto.ServiceProviderInvoiceDto;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.vo.*;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
public interface IServiceProviderService extends BaseService<ServiceProviderEntity> {

    /**
     * 获取服务商关联的所有商户
     *
     * @param query
     * @param serviceProviderId
     * @param keyword
     * @return
     */
    R<IPage<EnterprisesVO>> getEnterpriseByServiceProvider(Query query, Long serviceProviderId, String keyword);

    /**
     * 获取当前服务商银行卡信息
     *
     * @param serviceProviderId
     * @return
     */
    R<ServiceProviderBankCardVO> getBankCard(Long serviceProviderId);

    /**
     * 新增或修改当前服务商银行卡信息
     *
     * @param serviceProviderBankCardDto
     * @param serviceProviderId
     * @return
     */
    R<String> addOrUpdateBankCard(ServiceProviderBankCardDto serviceProviderBankCardDto, Long serviceProviderId);

    /**
     * 获取当前服务商联系人信息
     *
     * @param serviceProviderId
     * @return
     */
    R<ServiceProviderContactPersonVO> getContactPerson(Long serviceProviderId);

    /**
     * 新增或修改当前服务商联系人信息
     *
     * @param serviceProviderContactPersonDto
     * @param serviceProviderId
     * @return
     */
    R<String> addOrUpdateContactPerson(ServiceProviderContactPersonDto serviceProviderContactPersonDto, Long serviceProviderId);

    /**
     * 获取当前服务商开票信息
     *
     * @param serviceProviderId
     * @return
     */
    R<ServiceProviderInvoiceVO> getInvoice(Long serviceProviderId);

    /**
     * 新增或修改当前服务商开票信息
     *
     * @param serviceProviderInvoiceDto
     * @param serviceProviderId
     * @return
     */
    R<String> addOrUpdateInvoice(ServiceProviderInvoiceDto serviceProviderInvoiceDto, Long serviceProviderId);

}

