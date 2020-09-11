package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.ServiceProviderMakerEntity;
import com.lgyun.system.user.vo.RelMakerListVO;

/**
 * 服务商创客关联表 Service 接口
 *
 * @author liangfeihu
 * @since 2020-08-19 16:01:29
 */
public interface IServiceProviderMakerService extends BaseService<ServiceProviderMakerEntity> {

    /**
     * 查询当前服务商的所有关联创客
     *
     * @param page
     * @param serviceProviderId
     * @param keyword
     * @return
     */
    R<IPage<RelMakerListVO>> getServiceProviderMakers(IPage<RelMakerListVO> page, Long serviceProviderId, String keyword);

}

