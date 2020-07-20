package com.lgyun.system.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.PayMakerMapper;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.lgyun.system.order.service.IPayMakerService;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayMakerServiceImpl extends BaseServiceImpl<PayMakerMapper, PayMakerEntity> implements IPayMakerService {

}
