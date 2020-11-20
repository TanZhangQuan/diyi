package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.BureauServiceProviderStatus;
import com.lgyun.common.enumeration.RelBureauType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.RelBureauServiceProviderEntity;
import com.lgyun.system.user.vo.RelBureauServiceProviderVO;

/**
 * 相关局与服务商关联表 Service 接口
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
public interface IRelBureauServiceProviderService extends BaseService<RelBureauServiceProviderEntity> {

    /**
     * 查询匹配的服务商
     *
     * @param serviceProviderName
     * @param page
     * @return
     */
    R<IPage<RelBureauServiceProviderVO>> queryRelBureauServiceProvider(String serviceProviderName, RelBureauType relBureauType, IPage<RelBureauServiceProviderVO> page);

    /**
     * 添加匹配服务商
     *
     * @param serviceProviderIds
     * @param bureauId
     * @return
     */
    R<String> addRelBureauServiceProvider(String serviceProviderIds, Long bureauId);

    /**
     * 开启或关闭匹配服务商
     *
     * @param bureauServiceProviderId
     * @param bureauServiceProviderStatus
     * @return
     */
    R<String> updateBureauServiceProvider(Long bureauServiceProviderId, BureauServiceProviderStatus bureauServiceProviderStatus);

    /**
     * 撤销服务商
     *
     * @param bureauServiceProviderId
     * @return
     */
    R<String> deleteBureauServiceProvider(Long bureauServiceProviderId);

}

