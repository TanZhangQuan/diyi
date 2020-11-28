package com.lgyun.system.order.service;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.ContractApplyInvoiceDTO;
import com.lgyun.system.order.entity.InvoiceApplicationEntity;

/**
 * 开票申请：记录商户的总包开票申请 Service 接口
 *
 * @author tzq
 * @since 2020-08-11 16:00:00
 */
public interface IInvoiceApplicationService extends BaseService<InvoiceApplicationEntity> {

    /**
     * 申请总包发票
     *
     * @param contractApplyInvoiceDto
     * @param enterpriseId
     * @param payEnterpriseService
     * @return
     */
    R<String> contractApplyInvoice(ContractApplyInvoiceDTO contractApplyInvoiceDto, Long enterpriseId, IPayEnterpriseService payEnterpriseService);
}

