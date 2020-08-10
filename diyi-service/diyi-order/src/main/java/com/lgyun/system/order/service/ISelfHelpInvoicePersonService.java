package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.SelfHelpInvoicePersonEntity;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface ISelfHelpInvoicePersonService extends BaseService<SelfHelpInvoicePersonEntity> {

    /**
     * 根据身份证号码
     */
    SelfHelpInvoicePersonEntity findCardNo(String CardNo);
}

