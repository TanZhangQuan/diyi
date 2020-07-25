package com.lgyun.system.order.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoiceAuditState;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoiceMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceDetailService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailsVO;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.feign.IUserClient;
import com.lgyun.system.order.vo.SelfHelpInvoiceYearMonthMoneyVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoiceServiceImpl extends BaseServiceImpl<SelfHelpInvoiceMapper, SelfHelpInvoiceEntity> implements ISelfHelpInvoiceService {

    private final IUserClient iUserClient;
    private final ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;

    @Override
    @Transactional
    public R<String> submitSelfHelpInvoice(SelfHelpInvoiceDto selfHelpInvoiceDto) {
        if(!selfHelpInvoiceDto.getInvoicePeopleType().equals(MakerType.NATURALPERSON) && null == selfHelpInvoiceDto.getBusinessEnterpriseId()){
            R.fail("参数错误");
        }
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = new SelfHelpInvoiceEntity();
        BeanUtil.copy(selfHelpInvoiceDto, selfHelpInvoiceEntity);
        selfHelpInvoiceEntity.setInvoiceAuditState(InvoiceAuditState.NOTREVIEWED);
        save(selfHelpInvoiceEntity);
        String bizName = "";
        String socialCreditNo = "";
        if(selfHelpInvoiceDto.getInvoicePeopleType().equals(MakerType.INDIVIDUALENTERPRISE)){
            IndividualEnterpriseEntity individualEnterpriseEntity = iUserClient.individualEnterpriseFindById(selfHelpInvoiceDto.getBusinessEnterpriseId());
            bizName = individualEnterpriseEntity.getIbname();
            socialCreditNo = individualEnterpriseEntity.getIbtaxNo();
            selfHelpInvoiceEntity.setBusinessEnterpriseId(individualEnterpriseEntity.getId());
        }
        if(selfHelpInvoiceDto.getInvoicePeopleType().equals(MakerType.INDIVIDUALBUSINESS)){
            IndividualBusinessEntity individualBusinessEntity = iUserClient.individualBusinessById(selfHelpInvoiceDto.getBusinessEnterpriseId());
            bizName = individualBusinessEntity.getIbname();
            socialCreditNo = individualBusinessEntity.getIbtaxNo();
            selfHelpInvoiceEntity.setBusinessEnterpriseId(individualBusinessEntity.getId());
        }

        SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = new SelfHelpInvoiceDetailEntity();
        BeanUtil.copy(selfHelpInvoiceDto, selfHelpInvoiceDetailEntity);
        selfHelpInvoiceDetailEntity.setMakerId(selfHelpInvoiceDto.getApplyMakerId());
        selfHelpInvoiceDetailEntity.setNoneMakerInvoicePersonId(selfHelpInvoiceDto.getSelfHelpInvoicePersonId());
        selfHelpInvoiceDetailEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
        selfHelpInvoiceDetailEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
        selfHelpInvoiceDetailEntity.setBizName(bizName);
        selfHelpInvoiceDetailEntity.setAccountBalanceUrl(selfHelpInvoiceDto.getAccountBalanceUrl());
        selfHelpInvoiceDetailEntity.setSocialCreditNo(socialCreditNo);
        selfHelpInvoiceDetailService.save(selfHelpInvoiceDetailEntity);
        return R.success("申请成功");
    }

    @Override
    public R<SelfHelpInvoiceDetailsVO> getSelfHelpInvoiceDetails(Long selfHelpInvoiceId) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(selfHelpInvoiceId);
        if(!selfHelpInvoiceEntity.getInvoiceAuditState().equals(InvoiceAuditState.APPROVED)){
            return R.fail("自助开票在审核中，请耐心等候");
        }
        return R.data(baseMapper.getSelfHelpInvoiceDetails(selfHelpInvoiceId));
    }

    @Override
    public R<SelfHelpInvoiceYearMonthMoneyVO> yearMonthMoney(Long businessEnterpriseId, MakerType makerType) {
        SelfHelpInvoiceYearMonthMoneyVO selfHelpInvoiceYearMonthMoneyVO = baseMapper.yearMonthMoney(businessEnterpriseId, makerType);
        return R.data(selfHelpInvoiceYearMonthMoneyVO);
    }

}
