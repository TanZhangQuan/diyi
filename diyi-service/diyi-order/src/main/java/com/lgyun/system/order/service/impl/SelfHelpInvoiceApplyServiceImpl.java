package com.lgyun.system.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.SelfHelpInvoiceApplyMapper;
import com.lgyun.system.order.entity.SelfHelpInvoiceApplyEntity;
import com.lgyun.system.order.service.ISelfHelpInvoiceApplyService;

/**
 * 自助开票申请：记录自助开票主表的申请记录情况 Service 实现
 *
 * @author tzq
 * @since 2020-08-08 10:36:37
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoiceApplyServiceImpl extends BaseServiceImpl<SelfHelpInvoiceApplyMapper, SelfHelpInvoiceApplyEntity> implements ISelfHelpInvoiceApplyService {

}
