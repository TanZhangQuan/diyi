package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.SelfHelpInvoiceExpressEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoiceExpressMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceExpressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoiceExpressServiceImpl extends BaseServiceImpl<SelfHelpInvoiceExpressMapper, SelfHelpInvoiceExpressEntity> implements ISelfHelpInvoiceExpressService {

    @Override
    public SelfHelpInvoiceExpressEntity findBySelfHelpInvoiceApplyProviderId(Long selfHelpInvoiceApplyProviderId) {
        QueryWrapper<SelfHelpInvoiceExpressEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SelfHelpInvoiceExpressEntity::getSelfHelpInvoiceApplyProviderId, selfHelpInvoiceApplyProviderId);
        return baseMapper.selectOne(queryWrapper);
    }
}
