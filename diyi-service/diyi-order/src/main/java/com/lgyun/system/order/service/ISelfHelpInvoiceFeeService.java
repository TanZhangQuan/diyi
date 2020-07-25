package com.lgyun.system.order.service;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.ConfirmPaymentDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceFeeEntity;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface ISelfHelpInvoiceFeeService extends BaseService<SelfHelpInvoiceFeeEntity> {

    /**
     * 确认支付
     *
     * @param confirmPaymentDto
     * @return
     */
    R<String> confirmPayment(ConfirmPaymentDto confirmPaymentDto);
}

