package com.lgyun.system.order.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.PayReceiptEntity;
import com.lgyun.system.order.mapper.PayReceiptMapper;
import com.lgyun.system.order.service.IPayReceiptService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayReceiptServiceImpl extends BaseServiceImpl<PayReceiptMapper, PayReceiptEntity> implements IPayReceiptService {

}
