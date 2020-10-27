package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.MakerTaxRecordEntity;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
public interface IMakerTaxRecordService extends BaseService<MakerTaxRecordEntity> {

    /**
     *
     */
    MakerTaxRecordEntity findPayMakerId(Long payMakerId);
}

