package com.lgyun.system.order.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.entity.SelfHelpInvoiceFeeEntity;
import com.lgyun.system.order.entity.SelfHelpInvoicePersonEntity;
import com.lgyun.system.order.service.ISelfHelpInvoiceDetailService;
import com.lgyun.system.order.service.ISelfHelpInvoicePersonService;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.RunCompanyEntity;
import com.lgyun.system.user.feign.IUserClient;
import com.lgyun.system.user.service.IRunCompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoiceMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Service
@AllArgsConstructor
public class SelfHelpInvoiceServiceImpl extends BaseServiceImpl<SelfHelpInvoiceMapper, SelfHelpInvoiceEntity> implements ISelfHelpInvoiceService {

    private final IUserClient iUserClient;
    private final ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;
    private final IRunCompanyService runCompanyService;
    private final ISelfHelpInvoicePersonService selfHelpInvoicePersonService;

    @Override
    @Transactional
    public R submitSelfHelpInvoice(SelfHelpInvoiceDto selfHelpInvoiceDto) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = new SelfHelpInvoiceEntity();
        selfHelpInvoiceEntity.setCompanyId(selfHelpInvoiceDto.getRunCompanyId());
        selfHelpInvoiceEntity.setApplyMakerId(selfHelpInvoiceDto.getApplyMakerId());
        selfHelpInvoiceEntity.setInvoicePeopleType(selfHelpInvoiceDto.getInvoicePeopleType().getValue());
        selfHelpInvoiceEntity.setChargeMoneyNum(selfHelpInvoiceDto.getChargeMoneyNum());
        selfHelpInvoiceEntity.setServiceRate(selfHelpInvoiceDto.getServiceRate());
        selfHelpInvoiceEntity.setServiceAndTaxMoney(selfHelpInvoiceDto.getServiceAndTaxMoney());
        selfHelpInvoiceEntity.setServiceFee(selfHelpInvoiceDto.getServiceFee());
        selfHelpInvoiceEntity.setServiceTax(selfHelpInvoiceDto.getServiceTax());
        selfHelpInvoiceEntity.setServiceInvoiceFee(selfHelpInvoiceDto.getServiceInvoiceFee());
        selfHelpInvoiceEntity.setIdendityConfirmFee(selfHelpInvoiceDto.getIdendityConfirmFee());
        selfHelpInvoiceEntity.setAddressId(selfHelpInvoiceDto.getAddressId());
        selfHelpInvoiceEntity.setInvoiceState(InvoiceState.NOTREVIEWED);
        selfHelpInvoiceEntity.setCreateTime(new Date());
        selfHelpInvoiceEntity.setCreateUser(selfHelpInvoiceDto.getApplyMakerId());
        selfHelpInvoiceEntity.setUpdateTime(new Date());
        selfHelpInvoiceEntity.setUpdateUser(selfHelpInvoiceDto.getApplyMakerId());
        selfHelpInvoiceEntity.setIsDeleted(0);
        selfHelpInvoiceEntity.setStatus(1);
        save(selfHelpInvoiceEntity);
        String bizName = "";
        String socialCreditNo = "";
        if(selfHelpInvoiceDto.getInvoicePeopleType().equals(MakerType.ALONE)){
            IndividualEnterpriseEntity individualEnterpriseEntity = iUserClient.individualEnterpriseFindByMakerId(selfHelpInvoiceDto.getApplyMakerId());
            bizName = individualEnterpriseEntity.getIbname();
            socialCreditNo = individualEnterpriseEntity.getIbtaxNo();
        }
        if(selfHelpInvoiceDto.getInvoicePeopleType().equals(MakerType.INDIVIDUALBUSINESS)){
            IndividualBusinessEntity individualBusinessEntity = iUserClient.individualBusinessByMakerId(selfHelpInvoiceDto.getApplyMakerId());
            bizName = individualBusinessEntity.getIbname();
            socialCreditNo = individualBusinessEntity.getIbtaxNo();
        }
        SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = new SelfHelpInvoiceDetailEntity(selfHelpInvoiceEntity.getSelfHelpInvoiceId(),
                "",selfHelpInvoiceDto.getApplyMakerId(),selfHelpInvoiceDto.getSelfHelpInvoicePersonId(),bizName,socialCreditNo,selfHelpInvoiceDto.getInvoiceType(),
                selfHelpInvoiceDto.getChargeMoneyNum(),selfHelpInvoiceDto.getFlowContractUrl(),selfHelpInvoiceDto.getBusinessContractUrl(),selfHelpInvoiceDto.getBusinessContractUrl(),
                selfHelpInvoiceDto.getServiceInvoiceFee(),selfHelpInvoiceDto.getIdendityConfirmFee());
        selfHelpInvoiceDetailEntity.setCreateTime(new Date());
        selfHelpInvoiceDetailEntity.setCreateUser(selfHelpInvoiceDto.getApplyMakerId());
        selfHelpInvoiceDetailEntity.setUpdateTime(new Date());
        selfHelpInvoiceDetailEntity.setUpdateUser(selfHelpInvoiceDto.getApplyMakerId());
        selfHelpInvoiceDetailEntity.setIsDeleted(0);
        selfHelpInvoiceDetailEntity.setStatus(1);
        selfHelpInvoiceDetailService.save(selfHelpInvoiceDetailEntity);
        return R.success("申请成功");
    }

    @Override
    public R getSelfHelpInvoiceDetails(Long selfHelpInvoiceId) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(selfHelpInvoiceId);
        if(!selfHelpInvoiceEntity.getInvoiceState().equals(InvoiceState.APPROVED)){
            R.fail("自助开票在审核中，请耐心等候");
        }
        return R.data(baseMapper.getSelfHelpInvoiceDetails(selfHelpInvoiceId));
    }
}
