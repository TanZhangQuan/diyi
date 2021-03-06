package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.MakerInvoiceEntity;

/**
 *  Service 接口
 *
 * @author tzq
 * @since 2020-07-25 14:38:06
 */
public interface IMakerInvoiceService extends BaseService<MakerInvoiceEntity> {
    /**
     * 跟创客支付id
     *
     */
    MakerInvoiceEntity findPayMakerId(Long payMakerId);
}

