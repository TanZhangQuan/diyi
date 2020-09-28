package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.InvoicePrintState;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.SelfHelpInvoiceApplyState;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.SelfHelpInvoiceDTO;
import com.lgyun.system.order.entity.SelfHelpInvoiceApplyEntity;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.entity.SelfHelpInvoicePersonEntity;
import com.lgyun.system.order.excel.InvoiceListExcel;
import com.lgyun.system.order.mapper.SelfHelpInvoiceDetailMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceApplyService;
import com.lgyun.system.order.service.ISelfHelpInvoiceDetailService;
import com.lgyun.system.order.service.ISelfHelpInvoicePersonService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailVO;
import com.lgyun.system.order.vo.admin.SelfHelpInvoiceDetailAdminVO;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoiceDetailServiceImpl extends BaseServiceImpl<SelfHelpInvoiceDetailMapper, SelfHelpInvoiceDetailEntity> implements ISelfHelpInvoiceDetailService {

    private IUserClient userClient;
    private ISelfHelpInvoicePersonService selfHelpInvoicePersonService;
    @Autowired
    @Lazy
    private ISelfHelpInvoiceService selfHelpInvoiceService;
    private ISelfHelpInvoiceApplyService selfHelpInvoiceApplyService;

    @Override
    public R uploadDeliverSheetUrl(Long selfHelpInvoiceDetailId, String deliverSheetUrl) {
        SelfHelpInvoiceDetailEntity byId = getById(selfHelpInvoiceDetailId);
        if (null == byId) {
            return R.fail("自助开票详情id输入错误");
        }
        byId.setDeliverSheetUrl(deliverSheetUrl);
        saveOrUpdate(byId);
        return R.success("上传成功");
    }

    @Override
    @Transactional
    public void importSelfHelpInvoiceDetail(List<InvoiceListExcel> list) {
        //保存自助开票主表数据
        SelfHelpInvoiceDTO selfHelpInvoiceDto = list.get(0).getSelfHelpInvoiceDto();
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = new SelfHelpInvoiceEntity();
        selfHelpInvoiceEntity.setEnterpriseId(selfHelpInvoiceDto.getEnterpriseId());
        if (ObjectType.MAKERPEOPLE.equals(selfHelpInvoiceDto.getObjectType())) {
            selfHelpInvoiceEntity.setApplyMakerId(selfHelpInvoiceDto.getObjectId());
        }
        if (ObjectType.ENTERPRISEPEOPLE.equals(selfHelpInvoiceDto.getObjectType())) {
            selfHelpInvoiceEntity.setApplyEnterpriseId(selfHelpInvoiceDto.getObjectId());
        }
        selfHelpInvoiceEntity.setCurrentState(SelfHelpInvoiceApplyState.AUDITING);
        selfHelpInvoiceEntity.setInvoicePeopleType(selfHelpInvoiceDto.getInvoiceIdentityType());
        selfHelpInvoiceEntity.setListFile(selfHelpInvoiceDto.getListFile());
        selfHelpInvoiceEntity.setTotalPayProviderFee(selfHelpInvoiceDto.getChargeMoneyNum());
        selfHelpInvoiceEntity.setAddressId(selfHelpInvoiceDto.getAddressId());
        selfHelpInvoiceService.save(selfHelpInvoiceEntity);

        SelfHelpInvoiceApplyEntity selfHelpInvoiceApplyEntity = new SelfHelpInvoiceApplyEntity();
        selfHelpInvoiceApplyEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
        selfHelpInvoiceApplyEntity.setApplyDate(new Date());
        selfHelpInvoiceApplyEntity.setApplyState(SelfHelpInvoiceApplyState.AUDITING);
        selfHelpInvoiceApplyEntity.setApplyDesc("");
        selfHelpInvoiceApplyService.save(selfHelpInvoiceApplyEntity);
        if (ObjectType.MAKERPEOPLE.equals(selfHelpInvoiceDto.getObjectType())) {
            makerSelfHelpInvoice(list, selfHelpInvoiceDto, selfHelpInvoiceEntity);
        }
        if (ObjectType.ENTERPRISEPEOPLE.equals(selfHelpInvoiceDto.getObjectType())) {
            enterpriseSelfHelpInvoice(list, selfHelpInvoiceDto, selfHelpInvoiceEntity);
        }
    }

    @Override
    public Boolean getSelfHelpInvoiceDetails(Long selfHelpInvoiceId, Long selfHelpInvoiceDetailId) {
        QueryWrapper<SelfHelpInvoiceDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SelfHelpInvoiceDetailEntity::getSelfHelpInvoiceId, selfHelpInvoiceId);
        List<SelfHelpInvoiceDetailEntity> selfHelpInvoiceDetailEntities = baseMapper.selectList(queryWrapper);
        int count = 0;
        int num = 0;
        for (SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity : selfHelpInvoiceDetailEntities) {
            if (!selfHelpInvoiceDetailEntity.getInvoicePrintState().equals(InvoicePrintState.INVOICESUCCESS)) {
                count++;
                if (selfHelpInvoiceDetailEntity.getId() == selfHelpInvoiceDetailId) {
                    num++;
                }
            }
        }
        if ((count == 1 && num == 1) || count == 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<SelfHelpInvoiceDetailVO> getSelfHelpInvoiceId(Long selfHelpInvoiceId) {
        QueryWrapper<SelfHelpInvoiceDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SelfHelpInvoiceDetailEntity::getSelfHelpInvoiceId, selfHelpInvoiceId);
        List<SelfHelpInvoiceDetailEntity> selfHelpInvoiceDetailEntities = baseMapper.selectList(queryWrapper);
        List<SelfHelpInvoiceDetailVO> selfHelpInvoiceDetail = new ArrayList<>();
        for (SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity:selfHelpInvoiceDetailEntities){
            SelfHelpInvoiceDetailVO copy = BeanUtil.copy(selfHelpInvoiceDetailEntity, SelfHelpInvoiceDetailVO.class);
            selfHelpInvoiceDetail.add(copy);
        }
        return selfHelpInvoiceDetail;
    }

    @Override
    public List<SelfHelpInvoiceDetailAdminVO> getSelfHelpInvoiceIdAll(Long selfHelpInvoiceId) {
        return baseMapper.getSelfHelpInvoiceIdAll(selfHelpInvoiceId);
    }


    /**
     * 创客自助开票
     */
    private void makerSelfHelpInvoice(List<InvoiceListExcel> list, SelfHelpInvoiceDTO selfHelpInvoiceDto, SelfHelpInvoiceEntity selfHelpInvoiceEntity){
        for (InvoiceListExcel invoiceListExcel: list) {
            SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = new SelfHelpInvoiceDetailEntity();
            selfHelpInvoiceDetailEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
            InvoicePeopleType invoicePeopleType = selfHelpInvoiceDto.getInvoiceIdentityType();
            ;
            if (invoicePeopleType.equals(InvoicePeopleType.NATURALPERSON)) {
                MakerEntity makerEntity = userClient.makerFindByPhoneNumber(invoiceListExcel.getPhoneNumber());
                if (null != makerEntity && !selfHelpInvoiceDto.getObjectId().equals(makerEntity.getId())) {
                    continue;
                }
                if (null != makerEntity && selfHelpInvoiceDto.getObjectId().equals(makerEntity.getId())) {
                    selfHelpInvoiceDetailEntity.setMakerId(selfHelpInvoiceDto.getObjectId());
                }
                if (null == makerEntity) {
                    SelfHelpInvoicePersonEntity selfHelpInvoicePersonEntity = selfHelpInvoicePersonService.findCardNo(invoiceListExcel.getIdCardNo());
                    if (null == selfHelpInvoicePersonEntity) {
                        selfHelpInvoicePersonEntity = new SelfHelpInvoicePersonEntity();
                        selfHelpInvoicePersonEntity.setIdCardNo(invoiceListExcel.getIdCardNo());
                        selfHelpInvoicePersonEntity.setIdCardName(invoiceListExcel.getInvoicePeopleName());
                        selfHelpInvoicePersonEntity.setPhoneNumber(invoiceListExcel.getPhoneNumber());
                        selfHelpInvoicePersonService.save(selfHelpInvoicePersonEntity);
                    }
                    selfHelpInvoiceDetailEntity.setNoneMakerInvoicePersonId(selfHelpInvoicePersonEntity.getId());
                }
                selfHelpInvoiceDetailEntity.setInvoicePeopleType(InvoicePeopleType.NATURALPERSON);
                selfHelpInvoiceDetailEntity.setInvoicePeopleName(invoiceListExcel.getInvoicePeopleName());
            }
            if (invoicePeopleType.equals(InvoicePeopleType.INDIVIDUALBUSINESS)) {
                IndividualBusinessEntity byMakerIdAndIbtaxNoBusiness = userClient.findByMakerIdAndIbtaxNoBusiness(selfHelpInvoiceDto.getObjectId(), invoiceListExcel.getIbtaxNo());
                if (null == byMakerIdAndIbtaxNoBusiness) {
                    continue;
                }
                selfHelpInvoiceDetailEntity.setInvoicePeopleType(InvoicePeopleType.INDIVIDUALBUSINESS);
                selfHelpInvoiceDetailEntity.setInvoicePeopleName(invoiceListExcel.getInvoicePeopleName());
                selfHelpInvoiceDetailEntity.setAllKindEnterpriseId(byMakerIdAndIbtaxNoBusiness.getId());
            }
            if (invoicePeopleType.equals(InvoicePeopleType.INDIVIDUALENTERPRISE)) {
                IndividualEnterpriseEntity byMakerIdAndIbtaxNoEnterprise = userClient.findByMakerIdAndIbtaxNoEnterprise(selfHelpInvoiceDto.getObjectId(), invoiceListExcel.getIbtaxNo());
                if (null == byMakerIdAndIbtaxNoEnterprise) {
                    continue;
                }
                selfHelpInvoiceDetailEntity.setInvoicePeopleType(InvoicePeopleType.INDIVIDUALENTERPRISE);
                selfHelpInvoiceDetailEntity.setInvoicePeopleName(invoiceListExcel.getInvoicePeopleName());
                selfHelpInvoiceDetailEntity.setAllKindEnterpriseId(byMakerIdAndIbtaxNoEnterprise.getId());
            }
            selfHelpInvoiceDetailEntity.setInvoiceType(selfHelpInvoiceDto.getInvoiceType());
            selfHelpInvoiceDetailEntity.setChargeMoneyNum(selfHelpInvoiceDto.getChargeMoneyNum());
            selfHelpInvoiceDetailEntity.setFlowContractUrl(selfHelpInvoiceDto.getFlowContractUrl());
            selfHelpInvoiceDetailEntity.setBusinessContractUrl(selfHelpInvoiceDto.getBusinessContractUrl());
            selfHelpInvoiceDetailEntity.setDeliverSheetUrl(selfHelpInvoiceDto.getDeliverSheetUrl());
            selfHelpInvoiceDetailEntity.setAccountBalanceUrl(selfHelpInvoiceDto.getAccountBalanceUrl());
            save(selfHelpInvoiceDetailEntity);
        }
    }

    private void enterpriseSelfHelpInvoice(List<InvoiceListExcel> list, SelfHelpInvoiceDTO selfHelpInvoiceDto, SelfHelpInvoiceEntity selfHelpInvoiceEntity) {
        for (InvoiceListExcel invoiceListExcel : list) {
            SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = new SelfHelpInvoiceDetailEntity();
            selfHelpInvoiceDetailEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
            InvoicePeopleType invoicePeopleType = selfHelpInvoiceDto.getInvoiceIdentityType();
            if (invoicePeopleType.equals(InvoicePeopleType.NATURALPERSON)) {
                MakerEntity makerEntity = userClient.makerFindByPhoneNumber(invoiceListExcel.getPhoneNumber());
                if (null == makerEntity) {
                    MakerEntity makerEntity1 = userClient.makerAdd(invoiceListExcel.getInvoicePeopleName(), invoiceListExcel.getIdCardNo(), invoiceListExcel.getPhoneNumber(), selfHelpInvoiceDto.getObjectId());
                    selfHelpInvoiceDetailEntity.setMakerId(makerEntity1.getId());
                } else {
                    userClient.makerEnterpriseAdd(selfHelpInvoiceDto.getObjectId(), makerEntity.getId());
                    selfHelpInvoiceDetailEntity.setMakerId(makerEntity.getId());
                }
                selfHelpInvoiceDetailEntity.setInvoicePeopleType(InvoicePeopleType.NATURALPERSON);
                selfHelpInvoiceDetailEntity.setInvoicePeopleName(invoiceListExcel.getInvoicePeopleName());
            }
            if (invoicePeopleType.equals(InvoicePeopleType.INDIVIDUALBUSINESS)) {
                IndividualBusinessEntity byMakerIdAndIbtaxNoBusiness = userClient.findByIbtaxNoBusiness(invoiceListExcel.getIbtaxNo());
                if (null == byMakerIdAndIbtaxNoBusiness) {
                    continue;
                }
                selfHelpInvoiceDetailEntity.setInvoicePeopleType(InvoicePeopleType.INDIVIDUALBUSINESS);
                selfHelpInvoiceDetailEntity.setInvoicePeopleName(invoiceListExcel.getInvoicePeopleName());
                selfHelpInvoiceDetailEntity.setAllKindEnterpriseId(byMakerIdAndIbtaxNoBusiness.getId());
            }
            if (invoicePeopleType.equals(InvoicePeopleType.INDIVIDUALENTERPRISE)) {
                IndividualEnterpriseEntity byMakerIdAndIbtaxNoEnterprise = userClient.findByIbtaxNoEnterprise(invoiceListExcel.getIbtaxNo());
                if (null == byMakerIdAndIbtaxNoEnterprise) {
                    continue;
                }
                selfHelpInvoiceDetailEntity.setInvoicePeopleType(InvoicePeopleType.INDIVIDUALENTERPRISE);
                selfHelpInvoiceDetailEntity.setInvoicePeopleName(invoiceListExcel.getInvoicePeopleName());
                selfHelpInvoiceDetailEntity.setAllKindEnterpriseId(byMakerIdAndIbtaxNoEnterprise.getId());
            }
            selfHelpInvoiceDetailEntity.setInvoiceType(selfHelpInvoiceDto.getInvoiceType());
            selfHelpInvoiceDetailEntity.setChargeMoneyNum(selfHelpInvoiceDto.getChargeMoneyNum());
            selfHelpInvoiceDetailEntity.setFlowContractUrl(selfHelpInvoiceDto.getFlowContractUrl());
            selfHelpInvoiceDetailEntity.setBusinessContractUrl(selfHelpInvoiceDto.getBusinessContractUrl());
            selfHelpInvoiceDetailEntity.setDeliverSheetUrl(selfHelpInvoiceDto.getDeliverSheetUrl());
            selfHelpInvoiceDetailEntity.setAccountBalanceUrl(selfHelpInvoiceDto.getAccountBalanceUrl());
            save(selfHelpInvoiceDetailEntity);
        }
    }
}
