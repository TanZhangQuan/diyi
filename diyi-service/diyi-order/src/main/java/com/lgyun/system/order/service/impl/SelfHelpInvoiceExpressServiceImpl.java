package com.lgyun.system.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lgyun.system.order.mapper.SelfHelpInvoiceExpressMapper;
import com.lgyun.system.order.entity.SelfHelpInvoiceExpressEntity;
import com.lgyun.system.order.service.ISelfHelpInvoiceExpressService;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SelfHelpInvoiceExpressServiceImpl extends ServiceImpl<SelfHelpInvoiceExpressMapper, SelfHelpInvoiceExpressEntity> implements ISelfHelpInvoiceExpressService {

}
