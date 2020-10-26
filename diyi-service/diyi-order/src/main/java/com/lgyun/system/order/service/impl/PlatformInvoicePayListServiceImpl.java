package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.PlatformInvoicePayListEntity;
import com.lgyun.system.order.mapper.PlatformInvoicePayListMapper;
import com.lgyun.system.order.service.IPlatformInvoicePayListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 记录服务商开具给商户的总包发票关联的支付清单 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-11 14:25:28
 */
@Slf4j
@Service
@AllArgsConstructor
public class PlatformInvoicePayListServiceImpl extends BaseServiceImpl<PlatformInvoicePayListMapper, PlatformInvoicePayListEntity> implements IPlatformInvoicePayListService {

    @Override
    public PlatformInvoicePayListEntity findPayEnterpriseId(Long payEnterpriseId) {
        QueryWrapper<PlatformInvoicePayListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlatformInvoicePayListEntity::getPayEnterpriseId, payEnterpriseId);

        PlatformInvoicePayListEntity platformInvoicePayListEntity = baseMapper.selectOne(queryWrapper);
        return platformInvoicePayListEntity;
    }

    @Override
    public List<PlatformInvoicePayListEntity> findInvoicePrintId(Long invoicePrintId) {
        QueryWrapper<PlatformInvoicePayListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlatformInvoicePayListEntity::getInvoicePrintId, invoicePrintId);
        return baseMapper.selectList(queryWrapper);
    }
}
