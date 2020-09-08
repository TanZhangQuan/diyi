package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.enumeration.SelfHelpInvoiceApplyState;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.SelfHelpInvoiceSpEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoiceSpMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceSpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public SelfHelpInvoiceSpEntity findByServiceProviderIdAndSelfHelpInvoiceId(Long serviceProviderId, Long selfHelpInvoiceId) {

        QueryWrapper<SelfHelpInvoiceSpEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SelfHelpInvoiceSpEntity::getServiceProviderId, serviceProviderId)
                .eq(SelfHelpInvoiceSpEntity::getSelfHelpInvoiceId, selfHelpInvoiceId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public SelfHelpInvoiceSpEntity findBySelfHelpInvoiceIdAndAuditing(Long selfHelpInvoiceId) {

        QueryWrapper<SelfHelpInvoiceSpEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SelfHelpInvoiceSpEntity::getSelfHelpInvoiceId, selfHelpInvoiceId)
                .eq(SelfHelpInvoiceSpEntity::getApplyState, SelfHelpInvoiceApplyState.AUDITING);

        return baseMapper.selectOne(queryWrapper);
    }
}
