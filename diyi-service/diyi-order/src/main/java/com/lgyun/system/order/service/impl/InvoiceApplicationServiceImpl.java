package com.lgyun.system.order.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.system.order.dto.ContractApplyInvoiceDto;
import com.lgyun.system.order.entity.InvoiceApplicationPayListEntity;
import com.lgyun.system.order.service.IInvoiceApplicationPayListService;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lgyun.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.lgyun.system.order.mapper.InvoiceApplicationMapper;
import com.lgyun.system.order.entity.InvoiceApplicationEntity;
import com.lgyun.system.order.service.IInvoiceApplicationService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 开票申请：记录商户的总包开票申请 Service 实现
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
@Slf4j
@Service
@AllArgsConstructor
public class InvoiceApplicationServiceImpl extends BaseServiceImpl<InvoiceApplicationMapper, InvoiceApplicationEntity> implements IInvoiceApplicationService {

    private IInvoiceApplicationPayListService iInvoiceApplicationPayListService;

    private IUserClient iUserClient;

    @Override
    @Transactional
    public R contractApplyInvoice(ContractApplyInvoiceDto contractApplyInvoiceDto, Long enterpriseId) {
        InvoiceApplicationEntity invoiceApplicationEntity = new InvoiceApplicationEntity();
        invoiceApplicationEntity.setApplicationDesc(contractApplyInvoiceDto.getApplicationDesc());
        invoiceApplicationEntity.setVoiceTotalAmount(contractApplyInvoiceDto.getVoiceTotalAmount());
        invoiceApplicationEntity.setApplicationPerson(iUserClient.getEnterpriseById(enterpriseId).getEnterpriseName());
        invoiceApplicationEntity.setInvoiceCatalogId(contractApplyInvoiceDto.getInvoiceCatalogId());
        invoiceApplicationEntity.setApplicationDate(new Date());
        save(invoiceApplicationEntity);
        InvoiceApplicationPayListEntity invoiceApplicationPayListEntity = new InvoiceApplicationPayListEntity();
        invoiceApplicationPayListEntity.setPayEnterpriseId(contractApplyInvoiceDto.getPayEnterpriseId());
        invoiceApplicationPayListEntity.setApplicationId(invoiceApplicationEntity.getId());
        iInvoiceApplicationPayListService.save(invoiceApplicationPayListEntity);
        return R.success("申请成功");
    }
}
