package com.lgyun.system.order.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.AcceptPaysheetCsListEntity;
import com.lgyun.system.order.mapper.AcceptPaysheetCsListMapper;
import com.lgyun.system.order.service.IAcceptPaysheetCsListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 商户支付回单表 Service 实现
 *
 * @author tzq
 * @since 2020-10-29 19:55:38
 */
@Slf4j
@Service
@AllArgsConstructor
public class AcceptPaysheetCsListServiceImpl extends BaseServiceImpl<AcceptPaysheetCsListMapper, AcceptPaysheetCsListEntity> implements IAcceptPaysheetCsListService {

}
