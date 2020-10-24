package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.entity.EnterpriseServiceProviderEntity;
import com.lgyun.system.user.vo.*;

import java.util.List;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-28 14:53:11
 */
public interface IEnterpriseServiceProviderService extends BaseService<EnterpriseServiceProviderEntity> {

    /**
     * 查询服务商编号和名称
     *
     * @param enterpriseId
     * @param serviceProviderName
     * @param page
     * @return
     */
    R<IPage<ServiceProviderIdNameListVO>> queryServiceProviderIdAndNameList(Long enterpriseId, String serviceProviderName, IPage<ServiceProviderIdNameListVO> page);

    /**
     * 根据商户ID, 服务商ID查询关联
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @return
     */
    EnterpriseServiceProviderEntity findByEnterpriseIdServiceProviderId(Long enterpriseId, Long serviceProviderId);

    /**
     * 查询服务商关联的所有商户
     *
     * @param page
     * @param serviceProviderId
     * @param keyword
     * @return
     */
    R<IPage<EnterprisesVO>> getEnterpriseByServiceProvider(IPage<EnterprisesVO> page, Long serviceProviderId, String keyword);

    /**
     * 查询当前商户合作服务商
     *
     * @param enterpriseId
     * @param keyWord
     * @param page
     * @return
     */
    R<IPage<ServiceProvidersVO>> getServiceProvidersByEnterpriseId(Long enterpriseId, String keyWord, IPage<ServiceProvidersVO> page);

    /**
     * 查询当前服务商合作商户
     *
     * @param serviceProviderId
     * @param keyWord
     * @param page
     * @return
     */
    R<IPage<EnterprisesByProviderVO>> getEnterprtisesByServiceProviderId(Long serviceProviderId, String keyWord, IPage<EnterprisesByProviderVO> page);

    /**
     * 查询商户编号和名称
     *
     * @param page
     * @param serviceProviderId
     * @return
     */
    R<IPage<EnterpriseIdNameListVO>> queryEnterpriseIdAndNameList(Long serviceProviderId, String enterpriseName, IPage<EnterpriseIdNameListVO> page);

    /**
     * 商户匹配服务商
     *
     * @param enterpriseId
     * @param serviceProviderIdList
     * @param adminEntity
     * @return
     */
    R<String> relevanceEnterpriseServiceProvider(Long enterpriseId, List<Long> serviceProviderIdList, AdminEntity adminEntity);
}

