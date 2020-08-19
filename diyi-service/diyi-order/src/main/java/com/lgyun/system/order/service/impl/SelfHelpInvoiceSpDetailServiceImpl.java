package com.lgyun.system.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.SelfHelpInvoiceSpDetailMapper;
import com.lgyun.system.order.entity.SelfHelpInvoiceSpDetailEntity;
import com.lgyun.system.order.service.ISelfHelpInvoiceSpDetailService;

/**
 * 服务商开票明细：是从自助开票明细中选择过来的，信息是一致的 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-19 16:10:30
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoiceSpDetailServiceImpl extends BaseServiceImpl<SelfHelpInvoiceSpDetailMapper, SelfHelpInvoiceSpDetailEntity> implements ISelfHelpInvoiceSpDetailService {

}
