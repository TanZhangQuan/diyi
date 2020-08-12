package com.lgyun.system.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.PlatformInvoicePayListMapper;
import com.lgyun.system.order.entity.PlatformInvoicePayListEntity;
import com.lgyun.system.order.service.IPlatformInvoicePayListService;

/**
 * 记录服务商开具给商户的总包发票关联的支付清单 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-11 14:25:28
 */
@Slf4j
@Service
@AllArgsConstructor
public class PlatformInvoicePayListServiceImpl extends BaseServiceImpl<PlatformInvoicePayListMapper, PlatformInvoicePayListEntity> implements IPlatformInvoicePayListService {

}
