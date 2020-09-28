package com.lgyun.system.order.service;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.ContractApplyInvoiceDTO;
import com.lgyun.system.order.entity.InvoiceApplicationEntity;

/**
 * 开票申请：记录商户的总包开票申请 Service 接口
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
public interface IInvoiceApplicationService extends BaseService<InvoiceApplicationEntity> {

    R contractApplyInvoice(ContractApplyInvoiceDTO contractApplyInvoiceDto, Long enterpriseId, IPayEnterpriseService payEnterpriseService);
}

