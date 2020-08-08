package com.lgyun.system.order.service;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.excel.InvoiceListExcel;

import java.util.List;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface ISelfHelpInvoiceDetailService extends BaseService<SelfHelpInvoiceDetailEntity> {

    R uploadDeliverSheetUrl(Long selfHelpInvoiceDetailId,String deliverSheetUrl);

    void importSelfHelpInvoiceDetail(List<InvoiceListExcel> list);
}

