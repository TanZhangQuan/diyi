package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.PlatformInvoicePayListEntity;

import java.util.List;

/**
 * 记录服务商开具给商户的总包发票关联的支付清单 Service 接口
 *
 * @author tzq
 * @since 2020-08-11 14:25:28
 */
public interface IPlatformInvoicePayListService extends BaseService<PlatformInvoicePayListEntity> {

    List<PlatformInvoicePayListEntity> findInvoicePrintId(Long invoicePrintId);
}

