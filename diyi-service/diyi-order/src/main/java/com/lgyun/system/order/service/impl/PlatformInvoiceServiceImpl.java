package com.lgyun.system.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.PlatformInvoiceMapper;
import com.lgyun.system.order.entity.PlatformInvoiceEntity;
import com.lgyun.system.order.service.IPlatformInvoiceService;

/**
 * 总包发票信息：记录服务商开具给商户的总包发票，一次开票可能多个清单一起 Service 实现
 *
 * @author tzq
 * @since 2020-08-11 16:00:00
 */
@Slf4j
@Service
@AllArgsConstructor
public class PlatformInvoiceServiceImpl extends BaseServiceImpl<PlatformInvoiceMapper, PlatformInvoiceEntity> implements IPlatformInvoiceService {

}
