package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.EnterpriseServiceProviderEntity;
import com.lgyun.system.user.vo.CooperationEnterprisesListVO;
import com.lgyun.system.user.vo.CooperationServiceProviderListVO;
import com.lgyun.system.user.vo.EnterprisesVO;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-28 14:53:11
 */
public interface IEnterpriseServiceProviderService extends BaseService<EnterpriseServiceProviderEntity> {

    /**
     * 根据商户ID, 服务商ID查询关联
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @return
     */
    int queryCountByEnterpriseIdAndServiceProviderId(Long enterpriseId, Long serviceProviderId, CooperateStatus cooperateStatus);

    /**
     * 根据商户, 服务商查询关联
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @return
     */
    EnterpriseServiceProviderEntity findByEnterpriseAndServiceProvider(Long enterpriseId, Long serviceProviderId);

    /**
     * 查询服务商关联的所有商户
     *
     * @param page
     * @param serviceProviderId
     * @param keyword
     * @return
     */
    R<IPage<EnterprisesVO>> queryRelevanceEnterpriseList(Long serviceProviderId, String keyword, IPage<EnterprisesVO> page);

    /**
     * 查询当前商户合作服务商
     *
     * @param enterpriseId
     * @param serviceProviderName
     * @param page
     * @return
     */
    R<IPage<CooperationServiceProviderListVO>> queryCooperationServiceProviderList(Long enterpriseId, String serviceProviderName, IPage<CooperationServiceProviderListVO> page);

    /**
     * 查询当前服务商合作商户
     *
     * @param serviceProviderId
     * @param enterpriseName
     * @param page
     * @return
     */
    R<IPage<CooperationEnterprisesListVO>> queryCooperationEnterpriseList(Long serviceProviderId, String enterpriseName, IPage<CooperationEnterprisesListVO> page);

    /**
     * 创建商户服务商关联
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param matchDesc
     * @return
     */
    R<String> relevanceEnterpriseServiceProvider(Long enterpriseId, Long serviceProviderId, String matchDesc);

    /**
     * 更改商户服务商合作关系
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param cooperateStatus
     * @return
     */
    R<String> updateCooperationStatus(Long enterpriseId, Long serviceProviderId, CooperateStatus cooperateStatus);

}

