package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.ServiceProviderBankCardDto;
import com.lgyun.system.user.dto.ServiceProviderContactPersonDto;
import com.lgyun.system.user.dto.ServiceProviderInvoiceDto;
import com.lgyun.system.user.dto.admin.AddServiceProviderDTO;
import com.lgyun.system.user.dto.admin.QueryServiceProviderListDTO;
import com.lgyun.system.user.dto.admin.UpdateServiceProviderDTO;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.entity.User;
import com.lgyun.system.user.vo.EnterprisesVO;
import com.lgyun.system.user.vo.ServiceProviderBankCardVO;
import com.lgyun.system.user.vo.ServiceProviderContactPersonVO;
import com.lgyun.system.user.vo.ServiceProviderInvoiceVO;
import com.lgyun.system.user.vo.admin.QueryServiceProviderDetailServiceProviderVO;
import com.lgyun.system.user.vo.admin.QueryServiceProviderListVO;

/**
 * Service 接口
 *
 * @author tzq
 * @since 2020-07-25 14:38:06
 */
public interface IServiceProviderService extends BaseService<ServiceProviderEntity> {

    /**
     * 查询服务商名字是否已存在
     *
     * @param serviceProviderName
     * @param serviceProviderId
     * @return
     */
    Integer findCountByServiceProviderName(String serviceProviderName, Long serviceProviderId);

    /**
     * 查询统一社会信用代码是否已存在
     *
     * @param socialCreditNo
     * @param serviceProviderId
     * @return
     */
    Integer findCountBySocialCreditNo(String socialCreditNo, Long serviceProviderId);

    /**
     * 查询服务商关联的所有商户
     *
     * @param query
     * @param serviceProviderId
     * @param keyword
     * @return
     */
    R<IPage<EnterprisesVO>> getEnterpriseByServiceProvider(Query query, Long serviceProviderId, String keyword);

    /**
     * 查询当前服务商银行卡信息
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
     * 查询当前服务商联系人信息
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
     * 查询当前服务商开票信息
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

    /**
     * 查询所有服务商
     *
     * @param queryServiceProviderListDTO
     * @param page
     * @return
     */
    R<IPage<QueryServiceProviderListVO>> queryServiceProviderListAdmin(QueryServiceProviderListDTO queryServiceProviderListDTO, IPage<QueryServiceProviderListVO> page);

    /**
     * 根据id
     */
    ServiceProviderEntity getId(Long serviceProviderId);

    /**
     * 查询服务商基本信息
     *
     * @param serviceProviderId
     * @return
     */
    R<QueryServiceProviderDetailServiceProviderVO> queryServiceProviderDetailServiceProvider(Long serviceProviderId);

    /**
     * 添加服务商
     *
     * @param addServiceProviderDTO
     * @return
     */
    R<String> createServiceProvider(AddServiceProviderDTO addServiceProviderDTO, User user);

    /**
     * 编辑服务商
     *
     * @param updateServiceProviderDTO
     * @param user
     * @return
     */
    R<String> updateServiceProvider(UpdateServiceProviderDTO updateServiceProviderDTO, User user);

    /**
     * 更改服务商状态
     *
     * @param serviceProviderId
     * @param serviceProviderState
     * @return
     */
    R<String> updateServiceProviderState(Long serviceProviderId, AccountState serviceProviderState);

}

