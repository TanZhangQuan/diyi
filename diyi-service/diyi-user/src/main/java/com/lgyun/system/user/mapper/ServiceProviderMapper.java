package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.vo.ServiceProviderBankCardVO;
import com.lgyun.system.user.vo.ServiceProviderContactPersonVO;
import com.lgyun.system.user.vo.ServiceProviderInvoiceVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
@Mapper
public interface ServiceProviderMapper extends BaseMapper<ServiceProviderEntity> {

    /**
     * 获取当前服务商银行卡信息
     *
     * @param serviceProviderId
     * @return
     */
    ServiceProviderBankCardVO getBankCard(Long serviceProviderId);

    /**
     * 获取当前服务商联系人信息
     *
     * @param serviceProviderId
     * @return
     */
    ServiceProviderContactPersonVO getContactPerson(Long serviceProviderId);

    /**
     * 获取当前服务商开票信息
     *
     * @param serviceProviderId
     * @return
     */
    ServiceProviderInvoiceVO getInvoice(Long serviceProviderId);
}

