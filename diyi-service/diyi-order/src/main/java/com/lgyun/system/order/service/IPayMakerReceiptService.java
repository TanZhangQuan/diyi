package com.lgyun.system.order.service;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.PayMakerReceiptEntity;

/**
 * Service 接口
 *
 * @author liangfeihu
 * @since 2020-08-04 14:20:06
 */
public interface IPayMakerReceiptService extends BaseService<PayMakerReceiptEntity> {

    /**
     * 删除分包支付回单
     *
     * @param payMakerId
     * @return
     */
    void deleteByPayMakerId(Long payMakerId);

    /**
     * 上传支付回单
     *
     * @param serviceProviderId
     * @param payMakerId
     * @param makerPayReceiptUrls
     * @return
     */
    R<String> uploadPayMakerReceipt(Long serviceProviderId, Long payMakerId, String makerPayReceiptUrls);

}

