package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface ISelfHelpInvoiceService extends BaseService<SelfHelpInvoiceEntity> {

    /**
     * 提交自助接口
     */
    R submitSelfHelpInvoice(SelfHelpInvoiceDto selfHelpInvoiceDto);

    /**
     * 查询开票详情
     */
    R getSelfHelpInvoiceDetails(Long selfHelpInvoiceId);
}

