package com.lgyun.system.order.service;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface ISelfHelpInvoiceDetailService extends BaseService<SelfHelpInvoiceDetailEntity> {

    R uploadDeliverSheetUrl(Long selfHelpInvoiceDetailId,String deliverSheetUrl);
}

