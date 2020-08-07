package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoiceAuditState;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.KdniaoTrackQueryUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.PayListDto;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import com.lgyun.system.order.dto.SelfHelpInvoiceWebDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoiceMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceDetailService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.order.vo.PayListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailsVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Slf4j
@Service
@AllArgsConstructor
public class SelfHelpInvoiceServiceImpl extends BaseServiceImpl<SelfHelpInvoiceMapper, SelfHelpInvoiceEntity> implements ISelfHelpInvoiceService {

    private final ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;

    @Override
    @Transactional
    public R<String> submitSelfHelpInvoice(SelfHelpInvoiceDto selfHelpInvoiceDto) {

        //TODO(重写)

        if (!selfHelpInvoiceDto.getInvoicePeopleType().equals(InvoicePeopleType.NATURALPERSON)) {
            R.fail("参数错误");
        }
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = new SelfHelpInvoiceEntity();
        BeanUtil.copy(selfHelpInvoiceDto, selfHelpInvoiceEntity);
        selfHelpInvoiceEntity.setInvoiceAuditState(InvoiceAuditState.NOTREVIEWED);
        save(selfHelpInvoiceEntity);
        String bizName = "";
        String socialCreditNo = "";

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
    public R getSelfHelpInvoiceDetails(Long selfHelpInvoiceId) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(selfHelpInvoiceId);
        if(null == selfHelpInvoiceEntity){
            return R.fail("数据不存在");
        }
        if (!selfHelpInvoiceEntity.getInvoiceAuditState().equals(InvoiceAuditState.APPROVED)) {
            return R.fail("自助开票在审核中，请耐心等候");
        }
        KdniaoTrackQueryUtil api = new KdniaoTrackQueryUtil();
        String orderTracesByJson = "";
        Map map = new HashMap();
        try{
            if(null != selfHelpInvoiceEntity.getExpressCompanyName() && "" != selfHelpInvoiceEntity.getExpressCompanyName()){
                orderTracesByJson = api.getOrderTracesByJson(selfHelpInvoiceEntity.getExpressCompanyName(), selfHelpInvoiceEntity.getExpressSheetNo());
            }
        }catch (Exception e){
            log.error("查询物流错误",e);
        }
        SelfHelpInvoiceDetailsVO selfHelpInvoiceDetails = baseMapper.getSelfHelpInvoiceDetails(selfHelpInvoiceId);
        map.put("expressCompanyName",null != selfHelpInvoiceEntity.getExpressCompanyName() ? "" : selfHelpInvoiceEntity.getExpressCompanyName());
        map.put("expressSheetNo",null != selfHelpInvoiceEntity.getExpressSheetNo() ? "" : selfHelpInvoiceEntity.getExpressSheetNo());
        map.put("expressUpdatePerson",null != selfHelpInvoiceEntity.getExpressUpdatePerson() ? "" : selfHelpInvoiceEntity.getExpressUpdatePerson());
        map.put("expressUpdatePersonTel",null != selfHelpInvoiceEntity.getExpressUpdatePersonTel() ? "" : selfHelpInvoiceEntity.getExpressUpdatePersonTel());
        map.put("orderTracesByJson",orderTracesByJson);
        map.put("selfHelpInvoiceDetails",selfHelpInvoiceDetails);
        return R.data(map);
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long businessEnterpriseId, InvoicePeopleType invoicePeopleType) {
        return R.data(baseMapper.yearMonthMoney(businessEnterpriseId, makerType));
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long businessEnterpriseId, InvoicePeopleType invoicePeopleType) {
        return R.data(baseMapper.selfHelpInvoiceStatistics(businessEnterpriseId, makerType));
    }

    @Override
    public R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(IPage<SelfHelpInvoiceListVO> page, Long businessEnterpriseId, InvoicePeopleType invoicePeopleType) {

        switch (invoicePeopleType) {

            case INDIVIDUALENTERPRISE:
                return R.data(page.setRecords(baseMapper.selfHelpInvoiceListEnterprise(businessEnterpriseId, page)));

            case INDIVIDUALBUSINESS:
                return R.data(page.setRecords(baseMapper.selfHelpInvoiceListBusiness(businessEnterpriseId, page)));

            default:
                return R.fail("开票人身份类别有误");
        }

    }
    @Override
    public R findMakerTypeSelfHelpInvoice(IPage<SelfHelpInvoiceDetailsVO> page, Long enterpriseId, MakerType makerType) {
        return R.data(page.setRecords(baseMapper.findMakerTypeSelfHelpInvoice(enterpriseId,makerType, page)));
    }

    @Override
    public R<IPage<PayListVO>> getByDtoEnterprise(Long enterpriseId, PayListDto payListDto, IPage<PayListVO> page) {

        if (payListDto.getBeginDate() != null && payListDto.getEndDate() != null) {
            if (payListDto.getBeginDate().after(payListDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getByDtoEnterprise(enterpriseId, payListDto, page)));
    }

    @Override
    @Transactional
    public R submitWebSelfHelpInvoice(SelfHelpInvoiceWebDto selfHelpInvoiceWebDto) {

        //TODO(重写)

        if (!selfHelpInvoiceWebDto.getInvoicePeopleType().equals(MakerType.NATURALPERSON)) {
            R.fail("参数错误");
        }
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = new SelfHelpInvoiceEntity();
        BeanUtil.copy(selfHelpInvoiceWebDto, selfHelpInvoiceEntity);
        selfHelpInvoiceEntity.setInvoiceAuditState(InvoiceAuditState.NOTREVIEWED);
        save(selfHelpInvoiceEntity);
        String bizName = "";
        String socialCreditNo = "";

        SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = new SelfHelpInvoiceDetailEntity();
        BeanUtil.copy(selfHelpInvoiceWebDto, selfHelpInvoiceDetailEntity);
        selfHelpInvoiceDetailEntity.setInvoicePeopleName("稍后改");
        selfHelpInvoiceDetailEntity.setNoneMakerInvoicePersonId(selfHelpInvoiceWebDto.getSelfHelpInvoicePersonId());
        selfHelpInvoiceDetailEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
        selfHelpInvoiceDetailEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
        selfHelpInvoiceDetailEntity.setBizName(bizName);
        selfHelpInvoiceDetailEntity.setAccountBalanceUrl(selfHelpInvoiceWebDto.getAccountBalanceUrl());
        selfHelpInvoiceDetailEntity.setSocialCreditNo(socialCreditNo);
        selfHelpInvoiceDetailService.save(selfHelpInvoiceDetailEntity);
        return R.success("申请成功");
    }
}
