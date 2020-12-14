package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.SelfHelpInvoiceAccountEntity;
import com.lgyun.system.order.vo.SelfHelpInvoiceAccountVO;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface ISelfHelpInvoiceAccountService extends BaseService<SelfHelpInvoiceAccountEntity> {

    /**
     * 立即支付
     *
     * @return
     */
    SelfHelpInvoiceAccountVO immediatePayment();

}

