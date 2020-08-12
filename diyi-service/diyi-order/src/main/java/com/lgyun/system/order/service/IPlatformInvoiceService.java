package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.PlatformInvoiceEntity;

/**
 * 总包发票信息：记录服务商开具给商户的总包发票，一次开票可能多个清单一起 Service 接口
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
public interface IPlatformInvoiceService extends BaseService<PlatformInvoiceEntity> {

}

