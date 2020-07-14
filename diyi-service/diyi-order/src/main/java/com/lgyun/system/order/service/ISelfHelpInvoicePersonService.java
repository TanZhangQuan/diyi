package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.SelfHelpInvoicePersonDto;
import com.lgyun.system.order.entity.SelfHelpInvoicePersonEntity;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface ISelfHelpInvoicePersonService extends BaseService<SelfHelpInvoicePersonEntity> {
    /**
     * 根据创客Idc查询自助开票非创客开票人
     */
    R<IPage<SelfHelpInvoicePersonEntity>> findPersonMakerId(IPage<SelfHelpInvoicePersonEntity> page, Long makerId);

    /**
     * 新建开票人
     */
    R saveSelfHelpInvoicePerson(SelfHelpInvoicePersonDto selfHelpInvoicePersonDto, Long makerId);
}

