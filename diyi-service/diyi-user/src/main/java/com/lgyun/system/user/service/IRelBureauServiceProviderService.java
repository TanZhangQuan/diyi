package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.RelBureauType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.RelBureauServiceProviderEntity;
import com.lgyun.system.user.vo.CooperationServiceProviderListVO;

/**
 * 相关局与服务商关联表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
public interface IRelBureauServiceProviderService extends BaseService<RelBureauServiceProviderEntity> {

    /**
     * 相关局匹配服务商
     *
     * @param relBureauId
     * @param serviceProviderId
     * @param matchDesc
     * @return
     */
    R<String> relevanceRelBureauServiceProvider(Long relBureauId, Long serviceProviderId, String matchDesc);

    /**
     * 查询相关局-服务商关联
     *
     * @param relBureauId
     * @param serviceProviderId
     * @return
     */
    RelBureauServiceProviderEntity queryByAgentMainAndServiceProvider(Long relBureauId, Long serviceProviderId);

    /**
     * 查询相关局-服务商关联数
     *
     * @param relBureauType
     * @param serviceProviderId
     * @return
     */
    int queryRelBureauServiceProviderNum(RelBureauType relBureauType, Long serviceProviderId);

    /**
     * 查询相关局合作服务商
     *
     * @param relBureauId
     * @param serviceProviderName
     * @param page
     * @return
     */
    R<IPage<CooperationServiceProviderListVO>> queryCooperationServiceProviderList(Long relBureauId, String serviceProviderName, IPage<CooperationServiceProviderListVO> page);

    /**
     * 更改相关局-服务商合作关系
     *
     * @param relBureauId
     * @param serviceProviderId
     * @param cooperateStatus
     * @return
     */
    R<String> updateCooperationStatus(Long relBureauId, Long serviceProviderId, CooperateStatus cooperateStatus);
}

