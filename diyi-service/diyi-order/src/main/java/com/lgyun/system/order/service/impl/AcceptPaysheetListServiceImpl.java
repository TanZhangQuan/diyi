package com.lgyun.system.order.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.AcceptPaysheetListEntity;
import com.lgyun.system.order.mapper.AcceptPaysheetListMapper;
import com.lgyun.system.order.service.IAcceptPaysheetListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商户支付回单表 Service 实现
 *
 * @author tzq
 * @since 2020-10-29 19:55:38
 */
@Slf4j
@Service
@AllArgsConstructor
public class AcceptPaysheetListServiceImpl extends BaseServiceImpl<AcceptPaysheetListMapper, AcceptPaysheetListEntity> implements IAcceptPaysheetListService {

    @Override
    public List<Long> queryAcceptPaysheetIdList(Long payEnterpriseId) {
        return baseMapper.queryAcceptPaysheetIdList(payEnterpriseId);
    }

    @Override
    public void deleteAcceptPaysheetList(Long acceptPaysheetId) {
        baseMapper.deleteAcceptPaysheetList(acceptPaysheetId);
    }

}
