package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.MakerTaxRecordEntity;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
public interface IMakerTaxRecordService extends BaseService<MakerTaxRecordEntity> {

    /**
     * 根据分包查询税票
     *
     * @param payMakerId
     * @return
     */
    MakerTaxRecordEntity findPayMakerId(Long payMakerId);
}

