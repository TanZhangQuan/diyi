package com.lgyun.system.order.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.SelfHelpInvoiceAccountEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoiceAccountMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceAccountService;
import com.lgyun.system.order.vo.SelfHelpInvoiceAccountVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Service
@AllArgsConstructor
public class SelfHelpInvoiceAccountServiceImpl extends BaseServiceImpl<SelfHelpInvoiceAccountMapper, SelfHelpInvoiceAccountEntity> implements ISelfHelpInvoiceAccountService {

    @Override
    public SelfHelpInvoiceAccountVO immediatePayment() {
        return baseMapper.immediatePayment();
    }
}
