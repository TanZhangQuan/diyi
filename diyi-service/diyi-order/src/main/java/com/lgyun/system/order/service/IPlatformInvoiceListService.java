package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.PlatformInvoiceListEntity;

import java.util.List;

/**
 * 记录服务商开具给商户的总包发票 Service 接口
 *
 * @author liangfeihu
 * @since 2020-08-11 14:25:28
 */
public interface IPlatformInvoiceListService extends BaseService<PlatformInvoiceListEntity> {

    List<PlatformInvoiceListEntity> findInvoicePrintId(Long invoicePrintId);
}

