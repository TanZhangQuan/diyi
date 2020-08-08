package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.CollectionUtil;
import com.lgyun.common.tool.KdniaoTrackQueryUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import com.lgyun.system.order.dto.SelfHelpInvoicePayDto;
import com.lgyun.system.order.dto.SelfHelpInvoiceWebDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.mapper.SelfHelpInvoiceMapper;
import com.lgyun.system.order.service.ISelfHelpInvoiceDetailService;
import com.lgyun.system.order.service.ISelfHelpInvoicePersonService;
import com.lgyun.system.order.service.ISelfHelpInvoiceService;
import com.lgyun.system.order.vo.PayListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailsVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.feign.IUserClient;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.feign.IUserClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
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

    private IUserClient iUserClient;


    @Override
    public R<Map> getSelfHelpInvoiceDetails(Long selfHelpInvoiceId) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(selfHelpInvoiceId);
        if (null == selfHelpInvoiceEntity) {
            return R.fail("数据不存在");
        }
        if (selfHelpInvoiceEntity.getCurrentState().equals(ApplyState.EDITING) || selfHelpInvoiceEntity.getCurrentState().equals(ApplyState.UNDERREVIEW)) {
            return R.fail("自助开票在审核中，请耐心等候");
        }
        KdniaoTrackQueryUtil api = new KdniaoTrackQueryUtil();
        String orderTracesByJson = "";
        Map map = new HashMap();
        try {
            if (null != selfHelpInvoiceEntity.getExpressCompanyName() && "" != selfHelpInvoiceEntity.getExpressCompanyName()) {
                orderTracesByJson = api.getOrderTracesByJson(selfHelpInvoiceEntity.getExpressCompanyName(), selfHelpInvoiceEntity.getExpressSheetNo());
            }
        } catch (Exception e) {
            log.error("查询物流错误", e);
        }
        SelfHelpInvoiceDetailsVO selfHelpInvoiceDetails = baseMapper.getSelfHelpInvoiceDetails(selfHelpInvoiceId);
        map.put("expressCompanyName", null != selfHelpInvoiceEntity.getExpressCompanyName() ? "" : selfHelpInvoiceEntity.getExpressCompanyName());
        map.put("expressSheetNo", null != selfHelpInvoiceEntity.getExpressSheetNo() ? "" : selfHelpInvoiceEntity.getExpressSheetNo());
        map.put("expressUpdatePerson", null != selfHelpInvoiceEntity.getExpressUpdatePerson() ? "" : selfHelpInvoiceEntity.getExpressUpdatePerson());
        map.put("expressUpdatePersonTel", null != selfHelpInvoiceEntity.getExpressUpdatePersonTel() ? "" : selfHelpInvoiceEntity.getExpressUpdatePersonTel());
        map.put("orderTracesByJson", orderTracesByJson);
        map.put("selfHelpInvoiceDetails", selfHelpInvoiceDetails);
        return R.data(map);
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long allKindEnterpriseId, InvoicePeopleType invoicePeopleType) {
        return R.data(baseMapper.yearMonthMoney(allKindEnterpriseId, invoicePeopleType));
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long allKindEnterpriseId, InvoicePeopleType invoicePeopleType) {
        return R.data(baseMapper.selfHelpInvoiceStatistics(allKindEnterpriseId, invoicePeopleType));
    }

    @Override
    public R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(IPage<SelfHelpInvoiceListVO> page, Long allKindEnterpriseId, InvoicePeopleType invoicePeopleType) {

        List<SelfHelpInvoiceListVO> selfHelpInvoiceListVOs = baseMapper.selfHelpInvoiceList(allKindEnterpriseId, invoicePeopleType, page);


        BizType bizType = null;
        switch (invoicePeopleType) {

            case INDIVIDUALBUSINESS:
                IndividualEnterpriseEntity individualEnterpriseEntity = iUserClient.individualEnterpriseFindById(allKindEnterpriseId);
                bizType = individualEnterpriseEntity.getBizType();
                break;

            case INDIVIDUALENTERPRISE:
                IndividualBusinessEntity individualBusinessEntity = iUserClient.individualBusinessById(allKindEnterpriseId);
                bizType = individualBusinessEntity.getBizType();
                break;

            default:
                break;
        }

        if (bizType != null && CollectionUtil.isNotEmpty(selfHelpInvoiceListVOs)) {
            BizType finalBizType = bizType;
            selfHelpInvoiceListVOs.forEach(selfHelpInvoiceListVO -> {
                selfHelpInvoiceListVO.setBizType(finalBizType);
            });
        }

        return R.data(page.setRecords(selfHelpInvoiceListVOs));
    }

    @Override
    public R findMakerTypeSelfHelpInvoice(IPage<SelfHelpInvoiceDetailsVO> page, Long enterpriseId, MakerType makerType,String invoicePeopleName,String startTime,String endTime) {
        return R.data(page.setRecords(baseMapper.findMakerTypeSelfHelpInvoice(enterpriseId, makerType,invoicePeopleName,startTime,endTime, page)));
    }

    @Override
    public R<IPage<PayListVO>> getByDtoEnterprise(Long enterpriseId, SelfHelpInvoicePayDto selfHelpInvoicePayDto, IPage<PayListVO> page) {

        if (selfHelpInvoicePayDto.getBeginDate() != null && selfHelpInvoicePayDto.getEndDate() != null) {
            if (selfHelpInvoicePayDto.getBeginDate().after(selfHelpInvoicePayDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getByDtoEnterprise(enterpriseId, selfHelpInvoicePayDto, page)));
    }
}
