package com.lgyun.system.order.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.PayEntity;
import com.lgyun.system.order.mapper.PayMapper;
import com.lgyun.system.order.service.IPayService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author tzq
 * @since 2020-07-25 14:38:07
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayServiceImpl extends BaseServiceImpl<PayMapper, PayEntity> implements IPayService {

}
