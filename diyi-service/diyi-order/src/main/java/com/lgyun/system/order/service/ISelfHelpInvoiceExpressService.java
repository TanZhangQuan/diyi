package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.SelfHelpInvoiceExpressEntity;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface ISelfHelpInvoiceExpressService extends BaseService<SelfHelpInvoiceExpressEntity> {

    /**
     * 根据自助开票服务商ID查询自助开票快递
     *
     * @param selfHelpInvoiceApplyProviderId
     * @return
     */
    SelfHelpInvoiceExpressEntity findBySelfHelpInvoiceApplyProviderId(Long selfHelpInvoiceApplyProviderId);

}

