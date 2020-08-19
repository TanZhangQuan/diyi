package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.InvoiceApplicationPayListEntity;
import com.lgyun.system.order.vo.ApplicationVo;

import java.util.List;

/**
 * 总包开票申请关联的支付清单 Service 接口
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
public interface IInvoiceApplicationPayListService extends BaseService<InvoiceApplicationPayListEntity> {

    /**
     *
     */
    List<ApplicationVo> findApplication(Long payEnterpriseId);
}
