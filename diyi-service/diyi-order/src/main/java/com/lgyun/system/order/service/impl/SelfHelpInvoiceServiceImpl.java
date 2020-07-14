package com.lgyun.system.order.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoiceMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceDetailService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public R submitSelfHelpInvoice(SelfHelpInvoiceDto selfHelpInvoiceDto) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = new SelfHelpInvoiceEntity();
        BeanUtil.copy(selfHelpInvoiceDto, selfHelpInvoiceEntity);
        selfHelpInvoiceEntity.setInvoiceState(InvoiceState.NOTREVIEWED);
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

        SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = new SelfHelpInvoiceDetailEntity();
        BeanUtil.copy(selfHelpInvoiceDto, selfHelpInvoiceDetailEntity);
        selfHelpInvoiceDetailEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getSelfHelpInvoiceId());
        selfHelpInvoiceDetailEntity.setBizName(bizName);
        selfHelpInvoiceDetailEntity.setSocialCreditNo(socialCreditNo);
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
