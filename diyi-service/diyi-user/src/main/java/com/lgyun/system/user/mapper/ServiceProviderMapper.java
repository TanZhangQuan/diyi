package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.QueryServiceProviderListDTO;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author tzq
 * @since 2020-07-25 14:38:06
 */
@Mapper
public interface ServiceProviderMapper extends BaseMapper<ServiceProviderEntity> {

    /**
     * 查询当前服务商银行卡信息
     *
     * @param serviceProviderId
     * @return
     */
    ServiceProviderBankCardVO getBankCard(Long serviceProviderId);

    /**
     * 查询当前服务商联系人信息
     *
     * @param serviceProviderId
     * @return
     */
    ServiceProviderContactPersonVO getContactPerson(Long serviceProviderId);

    /**
     * 查询当前服务商开票信息
     *
     * @param serviceProviderId
     * @return
     */
    InvoiceVO getInvoice(Long serviceProviderId);

    /**
     * 查询所有服务商
     *
     * @param queryServiceProviderListDTO
     * @param page
     * @return
     */
    List<ServiceProviderListVO> queryServiceProviderListAdmin(QueryServiceProviderListDTO queryServiceProviderListDTO, IPage<ServiceProviderListVO> page);

    /**
     * 查询编辑服务商详情
     *
     * @param serviceProviderId
     * @return
     */
    ServiceProviderUpdateDetailVO queryServiceProviderUpdateDetail(Long serviceProviderId);

    /**
     * 支付管理模块查询所有服务商
     *
     * @param serviceProviderName
     * @param page
     * @return
     */
    List<ServiceProviderListPaymentVO> queryServiceProviderListPayment(String serviceProviderName, IPage<ServiceProviderListPaymentVO> page);

}

