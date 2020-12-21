package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.SelfHelpInvoiceApplyEntity;

/**
 * 自助开票申请：记录自助开票主表的申请记录情况 Service 接口
 *
 * @author tzq
 * @since 2020-08-08 10:36:37
 */
public interface ISelfHelpInvoiceApplyService extends BaseService<SelfHelpInvoiceApplyEntity> {

    /**
     * 通过自助开票主表id查询自助开票申请
      */
    SelfHelpInvoiceApplyEntity getBySelfHelpInvoiceId(Long selfHelpInvoiceId);

}

