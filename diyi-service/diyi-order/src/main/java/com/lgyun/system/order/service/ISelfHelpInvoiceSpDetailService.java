package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.SelfHelpInvoiceSpDetailEntity;

/**
 * 服务商开票明细：是从自助开票明细中选择过来的，信息是一致的 Service 接口
 *
 * @author tzq
 * @since 2020-08-19 16:10:30
 */
public interface ISelfHelpInvoiceSpDetailService extends BaseService<SelfHelpInvoiceSpDetailEntity> {

    /**
     * 根据自助开票明细查询服务商开票明细
     *
     * @param selfHelpInvoiceDetailId
     * @return
     */
    SelfHelpInvoiceSpDetailEntity findBySelfHelpInvoiceDetailId(Long selfHelpInvoiceDetailId);

}

