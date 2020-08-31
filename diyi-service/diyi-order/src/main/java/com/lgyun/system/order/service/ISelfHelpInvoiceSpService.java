package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.SelfHelpInvoiceSpEntity;

/**
 * 自助开票-服务商：记录自助开票主表的提交给不同服务商的 Service 接口
 *
 * @author liangfeihu
 * @since 2020-08-19 16:10:30
 */
public interface ISelfHelpInvoiceSpService extends BaseService<SelfHelpInvoiceSpEntity> {

    /**
     * 根据自主开票ID查询审核中的自主开票
     *
     * @param selfHelpInvoiceId
     * @return
     */
    SelfHelpInvoiceSpEntity findBySelfHelpInvoiceIdAndAuditing(Long selfHelpInvoiceId);
}

