package com.lgyun.system.order.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ApplyState;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
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
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *  Service 实现
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

    private ISelfHelpInvoiceService selfHelpInvoiceService;

    private ISelfHelpInvoiceApplyService selfHelpInvoiceApplyService;

    @Override
    public R uploadDeliverSheetUrl(Long selfHelpInvoiceDetailId, String deliverSheetUrl) {
        SelfHelpInvoiceDetailEntity byId = getById(selfHelpInvoiceDetailId);
        if(null == byId){
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
        SelfHelpInvoiceDto selfHelpInvoiceDto = list.get(0).getSelfHelpInvoiceDto();
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = new SelfHelpInvoiceEntity();
        selfHelpInvoiceEntity.setEnterpriseId(selfHelpInvoiceDto.getEnterpriseId());
        if(ObjectType.MAKERPEOPLE.equals(selfHelpInvoiceDto.getObjectType())){
            selfHelpInvoiceEntity.setApplyMakerId(selfHelpInvoiceDto.getObjectId());
        }
        if(ObjectType.ENTERPRISEPEOPLE.equals(selfHelpInvoiceDto.getObjectType())){
            selfHelpInvoiceEntity.setApplyEnterpriseId(selfHelpInvoiceDto.getObjectId());
        }
        selfHelpInvoiceEntity.setCurrentState(ApplyState.UNDERREVIEW);
        selfHelpInvoiceEntity.setInvoicePeopleType(selfHelpInvoiceDto.getInvoiceIdentityType());
        selfHelpInvoiceEntity.setListFile(selfHelpInvoiceDto.getListFile());
        selfHelpInvoiceEntity.setChargeMoneyNum(selfHelpInvoiceDto.getChargeMoneyNum());
        selfHelpInvoiceEntity.setAddressId(selfHelpInvoiceDto.getAddressId());
        selfHelpInvoiceService.save(selfHelpInvoiceEntity);

        SelfHelpInvoiceApplyEntity selfHelpInvoiceApplyEntity = new SelfHelpInvoiceApplyEntity();
        selfHelpInvoiceApplyEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
        selfHelpInvoiceApplyEntity.setApplyDate(new Date());
        selfHelpInvoiceApplyEntity.setApplyState(ApplyState.UNDERREVIEW);
        selfHelpInvoiceApplyEntity.setApplyDesc("");
        selfHelpInvoiceApplyService.save(selfHelpInvoiceApplyEntity);
        if(ObjectType.MAKERPEOPLE.equals(selfHelpInvoiceDto.getObjectType())){
            makerSelfHelpInvoice(list,selfHelpInvoiceDto,selfHelpInvoiceEntity);
        }
        if(ObjectType.ENTERPRISEPEOPLE.equals(selfHelpInvoiceDto.getObjectType())){
            enterpriseSelfHelpInvoice(list,selfHelpInvoiceDto,selfHelpInvoiceEntity);
        }
    }


    /**
     * 创客自助开票
     */
    private void makerSelfHelpInvoice(List<InvoiceListExcel> list,SelfHelpInvoiceDto selfHelpInvoiceDto,SelfHelpInvoiceEntity selfHelpInvoiceEntity){
        for (InvoiceListExcel invoiceListExcel: list) {
            SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = new SelfHelpInvoiceDetailEntity();
            selfHelpInvoiceDetailEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
            InvoicePeopleType invoicePeopleType = selfHelpInvoiceDto.getInvoiceIdentityType();;
            if(invoicePeopleType.equals(InvoicePeopleType.NATURALPERSON)){
                MakerEntity makerEntity = userClient.makerFindByPhoneNumber(invoiceListExcel.getPhoneNumber());
                if(null != makerEntity && selfHelpInvoiceDto.getObjectId() != makerEntity.getId()){
                    continue;
                }
                if(null != makerEntity && selfHelpInvoiceDto.getObjectId() == makerEntity.getId()){
                    selfHelpInvoiceDetailEntity.setMakerId(selfHelpInvoiceDto.getObjectId());
                }
                if(null == makerEntity){
                    SelfHelpInvoicePersonEntity selfHelpInvoicePersonEntity = selfHelpInvoicePersonService.findCardNo(invoiceListExcel.getIdCardNo());
                    if(null == selfHelpInvoicePersonEntity){
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
            if(invoicePeopleType.equals(InvoicePeopleType.INDIVIDUALBUSINESS)){
                IndividualBusinessEntity byMakerIdAndIbtaxNoBusiness = userClient.findByMakerIdAndIbtaxNoBusiness(selfHelpInvoiceDto.getObjectId(), invoiceListExcel.getIbtaxNo());
                if(null == byMakerIdAndIbtaxNoBusiness){
                    continue;
                }
                selfHelpInvoiceDetailEntity.setInvoicePeopleType(InvoicePeopleType.INDIVIDUALBUSINESS);
                selfHelpInvoiceDetailEntity.setInvoicePeopleName(invoiceListExcel.getInvoicePeopleName());
                selfHelpInvoiceDetailEntity.setAllKindEnterpriseID(byMakerIdAndIbtaxNoBusiness.getId());
            }
            if(invoicePeopleType.equals(InvoicePeopleType.INDIVIDUALENTERPRISE)){
                IndividualEnterpriseEntity byMakerIdAndIbtaxNoEnterprise = userClient.findByMakerIdAndIbtaxNoEnterprise(selfHelpInvoiceDto.getObjectId(), invoiceListExcel.getIbtaxNo());
                if(null == byMakerIdAndIbtaxNoEnterprise){
                    continue;
                }
                selfHelpInvoiceDetailEntity.setInvoicePeopleType(InvoicePeopleType.INDIVIDUALENTERPRISE);
                selfHelpInvoiceDetailEntity.setInvoicePeopleName(invoiceListExcel.getInvoicePeopleName());
                selfHelpInvoiceDetailEntity.setAllKindEnterpriseID(byMakerIdAndIbtaxNoEnterprise.getId());
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

    /**
     * 商户自助开票
     */
    private void enterpriseSelfHelpInvoice(List<InvoiceListExcel> list,SelfHelpInvoiceDto selfHelpInvoiceDto,SelfHelpInvoiceEntity selfHelpInvoiceEntity){
        for (InvoiceListExcel invoiceListExcel: list) {
            SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = new SelfHelpInvoiceDetailEntity();
            selfHelpInvoiceDetailEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
            InvoicePeopleType invoicePeopleType = selfHelpInvoiceDto.getInvoiceIdentityType();
            if(invoicePeopleType.equals(InvoicePeopleType.NATURALPERSON)){
                MakerEntity makerEntity = userClient.makerFindByPhoneNumber(invoiceListExcel.getPhoneNumber());
                if(null == makerEntity){
                    MakerEntity makerEntity1 = userClient.makerAdd(invoiceListExcel.getInvoicePeopleName(), invoiceListExcel.getIdCardNo(), invoiceListExcel.getPhoneNumber(), selfHelpInvoiceDto.getObjectId());
                    selfHelpInvoiceDetailEntity.setMakerId(makerEntity1.getId());
                }else{
                    userClient.makerEnterpriseAdd(selfHelpInvoiceDto.getObjectId(), makerEntity.getId());
                    selfHelpInvoiceDetailEntity.setMakerId(makerEntity.getId());
                }
                selfHelpInvoiceDetailEntity.setInvoicePeopleType(InvoicePeopleType.NATURALPERSON);
                selfHelpInvoiceDetailEntity.setInvoicePeopleName(invoiceListExcel.getInvoicePeopleName());
            }
            if(invoicePeopleType.equals(InvoicePeopleType.INDIVIDUALBUSINESS)){
                IndividualBusinessEntity byMakerIdAndIbtaxNoBusiness = userClient.findByIbtaxNoBusiness(invoiceListExcel.getIbtaxNo());
                if(null == byMakerIdAndIbtaxNoBusiness){
                    continue;
                }
                selfHelpInvoiceDetailEntity.setInvoicePeopleType(InvoicePeopleType.INDIVIDUALBUSINESS);
                selfHelpInvoiceDetailEntity.setInvoicePeopleName(invoiceListExcel.getInvoicePeopleName());
                selfHelpInvoiceDetailEntity.setAllKindEnterpriseID(byMakerIdAndIbtaxNoBusiness.getId());
            }
            if(invoicePeopleType.equals(InvoicePeopleType.INDIVIDUALENTERPRISE)){
                IndividualEnterpriseEntity byMakerIdAndIbtaxNoEnterprise = userClient.findByIbtaxNoEnterprise(invoiceListExcel.getIbtaxNo());
                if(null == byMakerIdAndIbtaxNoEnterprise){
                    continue;
                }
                selfHelpInvoiceDetailEntity.setInvoicePeopleType(InvoicePeopleType.INDIVIDUALENTERPRISE);
                selfHelpInvoiceDetailEntity.setInvoicePeopleName(invoiceListExcel.getInvoicePeopleName());
                selfHelpInvoiceDetailEntity.setAllKindEnterpriseID(byMakerIdAndIbtaxNoEnterprise.getId());
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
