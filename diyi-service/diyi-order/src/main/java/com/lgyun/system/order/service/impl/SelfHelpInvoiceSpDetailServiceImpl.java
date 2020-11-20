package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.SelfHelpInvoiceSpDetailEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoiceSpDetailMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceSpDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 服务商开票明细：是从自助开票明细中选择过来的，信息是一致的 Service 实现
 *
 * @author tzq
 * @since 2020-08-19 16:10:30
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoiceSpDetailServiceImpl extends BaseServiceImpl<SelfHelpInvoiceSpDetailMapper, SelfHelpInvoiceSpDetailEntity> implements ISelfHelpInvoiceSpDetailService {

    @Override
    public SelfHelpInvoiceSpDetailEntity findBySelfHelpInvoiceDetailId(Long selfHelpInvoiceDetailId) {

        QueryWrapper<SelfHelpInvoiceSpDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SelfHelpInvoiceSpDetailEntity::getSelfHelpInvoiceDetailId, selfHelpInvoiceDetailId);

        return baseMapper.selectOne(queryWrapper);

    }
}
