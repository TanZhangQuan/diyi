package com.lgyun.system.order.service.impl;

import com.lgyun.system.order.vo.ApplicationVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.mapper.InvoiceApplicationPayListMapper;
import com.lgyun.system.order.entity.InvoiceApplicationPayListEntity;
import com.lgyun.system.order.service.IInvoiceApplicationPayListService;

import java.util.List;

/**
 * 总包开票申请关联的支付清单 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
@Slf4j
@Service
@AllArgsConstructor
public class InvoiceApplicationPayListServiceImpl extends BaseServiceImpl<InvoiceApplicationPayListMapper, InvoiceApplicationPayListEntity> implements IInvoiceApplicationPayListService {

    @Override
    public List<ApplicationVO> findApplication(Long payEnterpriseId) {
        return baseMapper.findApplication(payEnterpriseId);
    }
}
