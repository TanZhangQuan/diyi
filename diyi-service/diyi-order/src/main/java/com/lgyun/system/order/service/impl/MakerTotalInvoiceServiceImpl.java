package com.lgyun.system.order.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.MakerTotalInvoiceMapper;
import com.lgyun.system.order.entity.MakerTotalInvoiceEntity;
import com.lgyun.system.order.service.IMakerTotalInvoiceService;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-18 20:49:12
 */
@Slf4j
@Service
@AllArgsConstructor
public class MakerTotalInvoiceServiceImpl extends BaseServiceImpl<MakerTotalInvoiceMapper, MakerTotalInvoiceEntity> implements IMakerTotalInvoiceService {

}
