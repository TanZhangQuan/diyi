package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.PlatformInvoiceListEntity;
import com.lgyun.system.order.mapper.PlatformInvoiceListMapper;
import com.lgyun.system.order.service.IPlatformInvoiceListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 记录服务商开具给商户的总包发票 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-11 14:25:28
 */
@Slf4j
@Service
@AllArgsConstructor
public class PlatformInvoiceListServiceImpl extends BaseServiceImpl<PlatformInvoiceListMapper, PlatformInvoiceListEntity> implements IPlatformInvoiceListService {

    @Override
    public List<PlatformInvoiceListEntity> findInvoicePrintId(Long invoicePrintId) {
        QueryWrapper<PlatformInvoiceListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PlatformInvoiceListEntity::getInvoicePrintId, invoicePrintId);
        return baseMapper.selectList(queryWrapper);
    }

}
