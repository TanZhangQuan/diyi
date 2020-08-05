package com.lgyun.system.order.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.AcceptPaysheetCsEntity;
import com.lgyun.system.order.mapper.AcceptPaysheetCsMapper;
import com.lgyun.system.order.service.IAcceptPaysheetCsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 众包交付支付验收单表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-05 10:43:29
 */
@Slf4j
@Service
@AllArgsConstructor
public class AcceptPaysheetCsServiceImpl extends BaseServiceImpl<AcceptPaysheetCsMapper, AcceptPaysheetCsEntity> implements IAcceptPaysheetCsService {

}
