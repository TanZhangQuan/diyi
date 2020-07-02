package com.lgyun.system.order.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoiceMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
@Service("selfHelpInvoiceService")
public class SelfHelpInvoiceServiceImpl extends ServiceImpl<SelfHelpInvoiceMapper, SelfHelpInvoiceEntity> implements ISelfHelpInvoiceService {

}
