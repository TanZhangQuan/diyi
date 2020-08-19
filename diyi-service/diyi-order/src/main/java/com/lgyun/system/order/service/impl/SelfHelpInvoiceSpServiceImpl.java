package com.lgyun.system.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.SelfHelpInvoiceSpMapper;
import com.lgyun.system.order.entity.SelfHelpInvoiceSpEntity;
import com.lgyun.system.order.service.ISelfHelpInvoiceSpService;

/**
 * 自助开票-服务商：记录自助开票主表的提交给不同服务商的 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-19 16:10:30
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoiceSpServiceImpl extends BaseServiceImpl<SelfHelpInvoiceSpMapper, SelfHelpInvoiceSpEntity> implements ISelfHelpInvoiceSpService {

}
