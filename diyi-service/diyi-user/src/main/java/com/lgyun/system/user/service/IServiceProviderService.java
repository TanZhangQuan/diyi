package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.*;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.vo.*;

/**
 * Service 接口
 *
 * @author tzq
 * @since 2020-07-25 14:38:06
 */
public interface IServiceProviderService extends BaseService<ServiceProviderEntity> {

    /**
     * 查询服务商是否已存在
     *
     * @param serviceProviderId
     * @return
     */
    int queryCountById(Long serviceProviderId);

    /**
     * 查询当前服务商联系人信息
     *
     * @param serviceProviderId
     * @return
     */
    R<ContactInfoVO> getContactPerson(Long serviceProviderId);

    /**
     * 新增或修改当前服务商联系人信息
     *
     * @param contactsInfoDTO
     * @param serviceProviderId
     * @return
     */
    R<String> updateContactPerson(Long serviceProviderId, ContactsInfoDTO contactsInfoDTO);

    /**
     * 查询当前服务商开票信息
     *
     * @param serviceProviderId
     * @return
     */
    R<InvoiceVO> queryeInvoice(Long serviceProviderId);

    /**
     * 查询所有服务商
     *
     * @param serviceProviderListDTO
     * @param page
     * @return
     */
    R<IPage<ServiceProviderListAdminVO>> queryServiceProviderListAdmin(ServiceProviderListDTO serviceProviderListDTO, IPage<ServiceProviderListAdminVO> page);

    /**
     * 查询编辑服务商详情
     *
     * @param serviceProviderId
     * @return
     */
    R<ServiceProviderUpdateDetailVO> queryServiceProviderUpdateDetail(Long serviceProviderId);

    /**
     * 添加服务商
     *
     * @param addServiceProviderDTO
     * @param adminEntity
     * @return
     */
    R<String> createServiceProvider(AddServiceProviderDTO addServiceProviderDTO, AdminEntity adminEntity);

    /**
     * 编辑服务商
     *
     * @param updateServiceProviderDTO
     * @param adminEntity
     * @return
     */
    R<String> updateServiceProvider(UpdateServiceProviderDTO updateServiceProviderDTO, AdminEntity adminEntity);

    /**
     * 更改服务商状态
     *
     * @param serviceProviderId
     * @param serviceProviderState
     * @return
     */
    R<String> updateServiceProviderState(Long serviceProviderId, AccountState serviceProviderState);

    /**
     * 支付管理模块查询所有服务商
     *
     * @param serviceProviderName
     * @param page
     * @return
     */
    R<IPage<ServiceProviderListPaymentVO>> queryServiceProviderListPayment(String serviceProviderName, IPage<ServiceProviderListPaymentVO> page);

    /**
     * 查询所有服务商
     *
     * @param page
     * @return
     */
    R getServiceAll(Long serviceProviderId, String serviceProviderName, IPage<ServiceProviderEntity> page);

    /**
     * 查询服务商编号名称
     *
     * @param enterpriseId
     * @param serviceProviderName
     * @param page
     * @return
     */
    R<IPage<ServiceProviderIdNameListVO>> queryServiceProviderIdAndNameList(Long enterpriseId, String serviceProviderName, IPage<ServiceProviderIdNameListVO> page);

    /**
     * 查询渠道商的所有服务商
     *
     * @param agentMainId
     * @param serviceProviderListDTO
     * @param page
     * @return
     */
    R<IPage<ServiceProviderListAdminVO>> queryServiceProviderListAgentMain(Long agentMainId, ServiceProviderListDTO serviceProviderListDTO, IPage<ServiceProviderListAdminVO> page);

    /**
     * 查询服务商详情
     *
     * @param serviceProviderId
     * @return
     */
    R<ServiceProviderDetailAgentMainVO> queryServiceProviderDetailAgentMain(Long serviceProviderId);
}

