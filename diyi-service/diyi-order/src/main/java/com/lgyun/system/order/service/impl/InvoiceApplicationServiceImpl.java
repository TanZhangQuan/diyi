package com.lgyun.system.order.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.ContractApplyInvoiceDTO;
import com.lgyun.system.order.entity.*;
import com.lgyun.system.order.mapper.InvoiceApplicationMapper;
import com.lgyun.system.order.service.*;
import com.lgyun.system.order.vo.ApplicationVO;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
    private IPlatformInvoicePayListService platformInvoicePayListService;
    private IPlatformInvoiceService platformInvoiceService;

    @Override
    @Transactional
    public R contractApplyInvoice(ContractApplyInvoiceDTO contractApplyInvoiceDto, Long enterpriseId, IPayEnterpriseService payEnterpriseService) {
        Long payEnterpriseId = contractApplyInvoiceDto.getPayEnterpriseId();
        PayEnterpriseEntity byId = payEnterpriseService.getById(payEnterpriseId);
        if(null == byId){
            return R.fail("支付清单不存在");
        }
        List<ApplicationVO> application = iInvoiceApplicationPayListService.findApplication(payEnterpriseId);
        if(application.size() > 0){
            return R.fail("申请记录已存在，请耐心等候！！！");
        }
        PlatformInvoicePayListEntity platformInvoicePayListEntity = platformInvoicePayListService.findPayEnterpriseId(payEnterpriseId);
        PlatformInvoiceEntity byId1 = platformInvoiceService.getById(platformInvoicePayListEntity.getInvoicePrintId());

        InvoiceApplicationEntity invoiceApplicationEntity = new InvoiceApplicationEntity();
        invoiceApplicationEntity.setApplicationDesc(contractApplyInvoiceDto.getApplicationDesc());
        invoiceApplicationEntity.setVoiceTotalAmount(contractApplyInvoiceDto.getVoiceTotalAmount());
        invoiceApplicationEntity.setApplicationPerson(iUserClient.queryEnterpriseById(enterpriseId).getEnterpriseName());
        invoiceApplicationEntity.setInvoiceCatalogId(contractApplyInvoiceDto.getInvoiceCatalogId());
        invoiceApplicationEntity.setApplicationDate(new Date());
        save(invoiceApplicationEntity);
        byId1.setApplicationId(invoiceApplicationEntity.getId());
        platformInvoiceService.saveOrUpdate(byId1);
        InvoiceApplicationPayListEntity invoiceApplicationPayListEntity = new InvoiceApplicationPayListEntity();
        invoiceApplicationPayListEntity.setPayEnterpriseId(contractApplyInvoiceDto.getPayEnterpriseId());
        invoiceApplicationPayListEntity.setApplicationId(invoiceApplicationEntity.getId());
        iInvoiceApplicationPayListService.save(invoiceApplicationPayListEntity);
        return R.success("申请成功");
    }
}
