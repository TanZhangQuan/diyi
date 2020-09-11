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
     * 根据自助开票，服务商查询自助开票-服务商
     *
     * @param serviceProviderId
     * @param selfHelpInvoiceId
     * @return
     */
    SelfHelpInvoiceSpEntity findByServiceProviderIdAndSelfHelpInvoiceId(Long serviceProviderId, Long selfHelpInvoiceId);

    /**
     * 根据自助开票ID查询审核中的自助开票
     *
     * @param selfHelpInvoiceId
     * @return
     */
    SelfHelpInvoiceSpEntity findBySelfHelpInvoiceIdAndAuditing(Long selfHelpInvoiceId);
}

