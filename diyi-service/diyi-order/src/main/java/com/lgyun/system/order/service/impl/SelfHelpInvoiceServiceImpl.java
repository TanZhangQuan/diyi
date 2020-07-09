package com.lgyun.system.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lgyun.system.order.mapper.SelfHelpInvoiceMapper;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SelfHelpInvoiceServiceImpl extends ServiceImpl<SelfHelpInvoiceMapper, SelfHelpInvoiceEntity> implements ISelfHelpInvoiceService {

}
