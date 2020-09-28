package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.dto.admin.QueryServiceProviderListDTO;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.vo.ServiceProviderBankCardVO;
import com.lgyun.system.user.vo.ServiceProviderContactPersonVO;
import com.lgyun.system.user.vo.ServiceProviderInvoiceVO;
import com.lgyun.system.user.vo.admin.ServiceProviderDetailServiceProviderVO;
import com.lgyun.system.user.vo.admin.ServiceProviderListVO;
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
    ServiceProviderInvoiceVO getInvoice(Long serviceProviderId);

    /**
     * 查询所有服务商
     *
     * @param queryServiceProviderListDTO
     * @param page
     * @return
     */
    List<ServiceProviderListVO> queryServiceProviderListAdmin(QueryServiceProviderListDTO queryServiceProviderListDTO, IPage<ServiceProviderListVO> page);

    /**
     * 查询服务商基本信息
     *
     * @param serviceProviderId
     * @return
     */
    ServiceProviderDetailServiceProviderVO queryServiceProviderDetailServiceProvider(Long serviceProviderId);
}

