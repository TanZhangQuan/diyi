package com.lgyun.system.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.PayEnterpriseReceiptMapper;
import com.lgyun.system.order.entity.PayEnterpriseReceiptEntity;
import com.lgyun.system.order.service.IPayEnterpriseReceiptService;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayEnterpriseReceiptServiceImpl extends BaseServiceImpl<PayEnterpriseReceiptMapper, PayEnterpriseReceiptEntity> implements IPayEnterpriseReceiptService {

}
