package com.lgyun.system.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.InvoiceApplicationPayListMapper;
import com.lgyun.system.order.entity.InvoiceApplicationPayListEntity;
import com.lgyun.system.order.service.IInvoiceApplicationPayListService;

/**
 * 总包开票申请关联的支付清单 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
@Slf4j
@Service
@AllArgsConstructor
public class InvoiceApplicationPayListServiceImpl extends BaseServiceImpl<InvoiceApplicationPayListMapper, InvoiceApplicationPayListEntity> implements IInvoiceApplicationPayListService {

}
