package com.lgyun.system.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.PlatformInvoiceListMapper;
import com.lgyun.system.order.entity.PlatformInvoiceListEntity;
import com.lgyun.system.order.service.IPlatformInvoiceListService;

/**
 * 记录服务商开具给商户的总包发票 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-11 14:25:28
 */
@Slf4j
@Service
@AllArgsConstructor
public class PlatformInvoiceListServiceImpl extends BaseServiceImpl<PlatformInvoiceListMapper, PlatformInvoiceListEntity> implements IPlatformInvoiceListService {

}
