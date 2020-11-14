package com.lgyun.system.order.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.ContractApplyInvoiceDTO;
import com.lgyun.system.order.entity.InvoiceApplicationEntity;
import com.lgyun.system.order.entity.InvoiceApplicationPayListEntity;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.mapper.InvoiceApplicationMapper;
import com.lgyun.system.order.service.*;
import com.lgyun.system.order.vo.ApplicationVO;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R contractApplyInvoice(ContractApplyInvoiceDTO contractApplyInvoiceDto, Long enterpriseId, IPayEnterpriseService payEnterpriseService) {
        String payEnterpriseIds = contractApplyInvoiceDto.getPayEnterpriseIds();
        String[] split = payEnterpriseIds.split(",");
        if(split.length <=0 ){
            return R.fail("参数有误");
        }
        for(int i = 0;i < split.length; i++){
            List<ApplicationVO> application = iInvoiceApplicationPayListService.findApplication(Long.parseLong(split[i]));
            if(application.size() > 0){
                return R.fail("申请记录已存在，请耐心等候");
            }
        }
        PayEnterpriseEntity payEnterpriseEntity = payEnterpriseService.getById(Long.parseLong(split[0]));
        for(int i = 1;i < split.length; i++){
            PayEnterpriseEntity byId1 = payEnterpriseService.getById(Long.parseLong(split[i]));
            if((payEnterpriseEntity.getEnterpriseId() != byId1.getEnterpriseId()) || (payEnterpriseEntity.getServiceProviderId() != byId1.getServiceProviderId())){
                return R.fail("请选择的服务商和商户相同的支付清单合并开票");
            }
        }
        InvoiceApplicationEntity invoiceApplicationEntity = new InvoiceApplicationEntity();
        invoiceApplicationEntity.setApplicationDesc(contractApplyInvoiceDto.getApplicationDesc());
        invoiceApplicationEntity.setApplicationPerson(iUserClient.queryEnterpriseById(enterpriseId).getEnterpriseName());
        invoiceApplicationEntity.setInvoiceCatalogId(contractApplyInvoiceDto.getInvoiceCatalogId());
        invoiceApplicationEntity.setApplicationDate(new Date());
        BigDecimal voiceTotalAmount = BigDecimal.ZERO;
        for(int i = 0;i < split.length; i++){
            PayEnterpriseEntity byId = payEnterpriseService.getById(Long.parseLong(split[i]));
            voiceTotalAmount = voiceTotalAmount.subtract(byId.getTotalMakerNetIncome());
        }
        invoiceApplicationEntity.setVoiceTotalAmount(voiceTotalAmount);
        save(invoiceApplicationEntity);
        for(int i = 0;i < split.length; i++){
            InvoiceApplicationPayListEntity invoiceApplicationPayListEntity = new InvoiceApplicationPayListEntity();
            invoiceApplicationPayListEntity.setPayEnterpriseId(Long.parseLong(split[i]));
            invoiceApplicationPayListEntity.setApplicationId(invoiceApplicationEntity.getId());
            iInvoiceApplicationPayListService.save(invoiceApplicationPayListEntity);
        }
        return R.success("申请成功");
    }
}
