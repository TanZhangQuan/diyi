package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.order.entity.PayEntity;
import com.lgyun.system.order.mapper.PayMapper;
import com.lgyun.system.order.service.IPayService;
import org.springframework.stereotype.Service;


/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-06-26 16:57:54
 */
@Service("payService")
public class PayServiceImpl extends ServiceImpl<PayMapper, PayEntity> implements IPayService {

    @Override
    public Integer queryRobOrderCount(Long orderId) {
        return baseMapper.queryRobOrderCount(orderId);
    }
}
