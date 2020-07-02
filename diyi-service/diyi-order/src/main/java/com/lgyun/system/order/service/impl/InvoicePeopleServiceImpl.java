package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.order.entity.InvoicePeopleEntity;
import com.lgyun.system.order.mapper.InvoicePeopleMapper;
import com.lgyun.system.order.service.IInvoicePeopleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
@Service("invoicePeopleService")
public class InvoicePeopleServiceImpl extends ServiceImpl<InvoicePeopleMapper, InvoicePeopleEntity> implements IInvoicePeopleService {

    @Override
    public List<InvoicePeopleEntity> findInvoicePeopleMakerId(Long makerId) {
        return baseMapper.findInvoicePeopleMakerId(makerId);
    }
}
