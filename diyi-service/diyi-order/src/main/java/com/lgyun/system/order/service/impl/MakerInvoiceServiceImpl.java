package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.MakerInvoiceEntity;
import com.lgyun.system.order.mapper.MakerInvoiceMapper;
import com.lgyun.system.order.service.IMakerInvoiceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
@Slf4j
@Service
@AllArgsConstructor
public class MakerInvoiceServiceImpl extends BaseServiceImpl<MakerInvoiceMapper, MakerInvoiceEntity> implements IMakerInvoiceService {

    @Override
    public MakerInvoiceEntity findPayMakerId(Long payMakerId) {
        QueryWrapper<MakerInvoiceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerInvoiceEntity::getPayMakerId,payMakerId);
        List<MakerInvoiceEntity> makerInvoiceEntities = baseMapper.selectList(queryWrapper);
        if(makerInvoiceEntities.size() <= 0){
            return null;
        }
        return makerInvoiceEntities.get(0);
    }
}
