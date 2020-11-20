package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.PayEnterpriseReceiptEntity;

/**
 * Service 接口
 *
 * @author tzq
 * @since 2020-07-17 20:01:13
 */
public interface IPayEnterpriseReceiptService extends BaseService<PayEnterpriseReceiptEntity> {

    /**
     * 根据支付清单id查询
     */
    String findEnterprisePayReceiptUrl(Long payEnterpriseId);

    /**
     * 删除总包支付回单
     *
     * @param payEnterpriseId
     * @return
     */
    void deleteByPayEnterpriseId(Long payEnterpriseId);

}

