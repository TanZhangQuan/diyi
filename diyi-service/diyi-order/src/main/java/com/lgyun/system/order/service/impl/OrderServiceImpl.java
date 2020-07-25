package com.lgyun.system.order.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.OrderEntity;
import com.lgyun.system.order.mapper.OrderMapper;
import com.lgyun.system.order.service.IOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:07
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, OrderEntity> implements IOrderService {

}
