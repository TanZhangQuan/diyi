package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.EnterpriseProviderEntity;
import com.lgyun.system.user.vo.EnterprisesByProviderVO;
import com.lgyun.system.user.vo.EnterprisesVO;
import com.lgyun.system.user.vo.ServiceProviderIdNameListVO;
import com.lgyun.system.user.vo.ServiceProvidersVO;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-28 14:53:11
 */
public interface IEnterpriseProviderService extends BaseService<EnterpriseProviderEntity> {

    /**
     * 根据商户ID查询所有合作的服务商
     *
     * @param page
     * @param enterpriseId
     * @return
     */
    R<IPage<ServiceProviderIdNameListVO>> getServiceProviderByEnterpriseId(IPage<ServiceProviderIdNameListVO> page, Long enterpriseId, String serviceProviderName);

    /**
     * 根据商户ID, 服务商ID查询关联
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @return
     */
    EnterpriseProviderEntity findByEnterpriseIdServiceProviderId(Long enterpriseId, Long serviceProviderId);

    /**
     * 获取服务商关联的所有商户
     *
     * @param page
     * @param serviceProviderId
     * @return
     */
    R<IPage<EnterprisesVO>> getEnterpriseByServiceProvider(IPage<EnterprisesVO> page, Long serviceProviderId);

    /**
     * 获取当前商户合作服务商
     *
     * @param enterpriseId
     * @param keyWord
     * @param page
     * @return
     */
    R<IPage<ServiceProvidersVO>> getServiceProvidersByEnterpriseId(Long enterpriseId, String keyWord, IPage<ServiceProvidersVO> page);

    /**
     * 获取当前服务商合作商户
     *
     * @param serviceProviderId
     * @param keyWord
     * @param page
     * @return
     */
    R<IPage<EnterprisesByProviderVO>> getEnterprtisesByServiceProviderId(Long serviceProviderId, String keyWord, IPage<EnterprisesByProviderVO> page);
}

