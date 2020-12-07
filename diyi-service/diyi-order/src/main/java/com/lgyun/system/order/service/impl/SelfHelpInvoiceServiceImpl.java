package com.lgyun.system.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.CollectionUtil;
import com.lgyun.common.tool.KdniaoTrackQueryUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.*;
import com.lgyun.system.order.entity.*;
import com.lgyun.system.order.mapper.SelfHelpInvoiceMapper;
import com.lgyun.system.order.service.*;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;
import com.lgyun.system.user.feign.IUserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
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
@RequiredArgsConstructor
public class SelfHelpInvoiceServiceImpl extends BaseServiceImpl<SelfHelpInvoiceMapper, SelfHelpInvoiceEntity> implements ISelfHelpInvoiceService {

    private final IUserClient iUserClient;
    private final ISelfHelpInvoiceSpService selfHelpInvoiceSpService;
    private final ISelfHelpInvoiceSpDetailService selfHelpInvoiceSpDetailService;
    private final ISelfHelpInvoiceExpressService selfHelpInvoiceExpressService;
    private final ISelfHelpInvoiceAccountService selfHelpInvoiceAccountService;
    private final ISelfHelpInvoiceFeeService selfHelpInvoiceFeeService;

    @Autowired
    @Lazy
    private ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;

    @Override
    public R<IPage<SelfHelpInvoiceListByEnterpriseVO>> getSelfHelfInvoicesByEnterprise(Long enterpriseId, MakerType makerType, SelfHelpInvoicesByEnterpriseDTO selfHelpInvoicesByEnterpriseDto, IPage<SelfHelpInvoiceListByEnterpriseVO> page) {

        if (selfHelpInvoicesByEnterpriseDto.getBeginDate() != null && selfHelpInvoicesByEnterpriseDto.getEndDate() != null) {
            if (selfHelpInvoicesByEnterpriseDto.getBeginDate().after(selfHelpInvoicesByEnterpriseDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getSelfHelfInvoicesByEnterprise(enterpriseId, makerType, selfHelpInvoicesByEnterpriseDto, page)));
    }

    @Override
    public R<SelfHelpInvoiceSingleByEnterpriseVO> getSingleSelfHelfInvoiceByEnterprise(Long enterpriseId, Long selfHelpInvoiceId) {
        return R.data(baseMapper.getSingleSelfHelfInvoiceByEnterprise(enterpriseId, selfHelpInvoiceId));
    }

    @Override
    public R<IPage<SelfHelpInvoiceDetailListVO>> getSelfHelfInvoiceDetailListBySelfHelfInvoice(Long selfHelpInvoiceId, IPage<SelfHelpInvoiceDetailListVO> page) {

        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(selfHelpInvoiceId);
        if (selfHelpInvoiceEntity == null) {
            return R.fail("自助开票不存在");
        }

        return R.data(page.setRecords(baseMapper.getSelfHelfInvoiceDetailListBySelfHelfInvoice(selfHelpInvoiceId, page)));
    }

    @Override
    public R<IPage<SelfHelpInvoiceExpressByEnterpriseProviderVO>> getSelfHelfInvoiceExpressBySelfHelfInvoiceAndEnterprise(Long enterpriseId, Long selfHelpInvoiceId, IPage<SelfHelpInvoiceExpressByEnterpriseProviderVO> page) {

        List<SelfHelpInvoiceExpressByEnterpriseProviderVO> selfHelpInvoiceExpressByEnterpriseVOList = baseMapper.getSelfHelfInvoiceExpressBySelfHelfInvoiceAndEnterprise(selfHelpInvoiceId, enterpriseId, page);
        //根据快递公司，快递单号查询快递信息
        for (SelfHelpInvoiceExpressByEnterpriseProviderVO selfHelpInvoiceExpressByEnterpriseVO : selfHelpInvoiceExpressByEnterpriseVOList) {
            KdniaoTrackQueryUtil kdniaoTrackQueryUtil = new KdniaoTrackQueryUtil();
            String expressMessage = null;

            if (StringUtils.isNotBlank(selfHelpInvoiceExpressByEnterpriseVO.getExpressCompanyName()) && StringUtils.isNotBlank(selfHelpInvoiceExpressByEnterpriseVO.getExpressNo())) {
                try {
                    expressMessage = kdniaoTrackQueryUtil.getOrderTracesByJson(selfHelpInvoiceExpressByEnterpriseVO.getExpressCompanyName(), selfHelpInvoiceExpressByEnterpriseVO.getExpressNo());
                    Map<String,Object> maps = (Map) JSON.parse(expressMessage);
                    Boolean success = (Boolean) maps.get("Success");
                    if(success){
                        selfHelpInvoiceExpressByEnterpriseVO.setExpressMessage(maps.get("Traces"));
                    }else{
                        selfHelpInvoiceExpressByEnterpriseVO.setExpressMessage("");
                    }
                } catch (Exception e) {
                    log.error("查询快递异常", e);
                }
            }

        }

        return R.data(page.setRecords(selfHelpInvoiceExpressByEnterpriseVOList));
    }

    @Override
    public R<IPage<SelfHelpInvoiceListByServiceProviderVO>> getSelfHelfInvoicesByServiceProvider(Long serviceProviderId, MakerType makerType, SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState, SelfHelpInvoiceDetailsByServiceProviderDTO selfHelpInvoiceDetailsByServiceProviderDto, IPage<SelfHelpInvoiceListByServiceProviderVO> page) {

        if (selfHelpInvoiceDetailsByServiceProviderDto.getBeginDate() != null && selfHelpInvoiceDetailsByServiceProviderDto.getEndDate() != null) {
            if (selfHelpInvoiceDetailsByServiceProviderDto.getBeginDate().after(selfHelpInvoiceDetailsByServiceProviderDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getSelfHelfInvoicesByServiceProvider(serviceProviderId, makerType, selfHelpInvoiceSpApplyState, selfHelpInvoiceDetailsByServiceProviderDto, page)));
    }

    @Override
    public R<SelfHelpInvoiceSingleByServiceProviderVO> getSingleSelfHelfInvoiceByServiceProvider(Long serviceProviderId, Long selfHelpInvoiceDetailId) {
        return R.data(baseMapper.getSingleSelfHelfInvoiceByServiceProvider(serviceProviderId, selfHelpInvoiceDetailId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> uploadInvoiceTaxByProvider(Long serviceProviderWorkerId, SelfHelpInvoiceDetailInvoiceTaxDTO selfHelpInvoiceDetailInvoiceTaxDto) {

        SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = selfHelpInvoiceDetailService.getById(selfHelpInvoiceDetailInvoiceTaxDto.getSelfHelpInvoiceDetailId());
        if (selfHelpInvoiceDetailEntity == null) {
            return R.fail("自助开票明细不存在");
        }

        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(selfHelpInvoiceDetailEntity.getSelfHelpInvoiceId());
        if (selfHelpInvoiceEntity == null) {
            return R.fail("自助开票不存在");
        }

        if (!(SelfHelpInvoiceApplyState.PAID.equals(selfHelpInvoiceEntity.getCurrentState())) && !(SelfHelpInvoiceApplyState.INVOICED.equals(selfHelpInvoiceEntity.getCurrentState()))) {
            return R.fail("自助开票的开票状态有误");
        }

        if (!(InvoicePrintState.INVOICEING.equals(selfHelpInvoiceDetailEntity.getInvoicePrintState())) && !(InvoicePrintState.INVOICESUCCESS.equals(selfHelpInvoiceDetailEntity.getInvoicePrintState()))) {
            return R.fail("自助开票明细的开票状态有误");
        }

        //todo
        //判断自助明细是否属于当前服务商
        SelfHelpInvoiceSpEntity selfHelpInvoiceSpEntity = selfHelpInvoiceSpService.findByServiceProviderIdAndSelfHelpInvoiceId(serviceProviderWorkerId, selfHelpInvoiceDetailEntity.getSelfHelpInvoiceId());
        if (selfHelpInvoiceSpEntity == null) {
            return R.fail("自助开票明细不属于当前服务商");
        }

        if (MakerType.NATURALPERSON.equals(selfHelpInvoiceDetailEntity.getMakerType()) && StringUtils.isBlank(selfHelpInvoiceDetailInvoiceTaxDto.getTaxScanPictures())) {
            return R.fail("自然人类型发票不能缺少税票");
        }

        SelfHelpInvoiceSpDetailEntity selfHelpInvoiceSpDetailEntity = selfHelpInvoiceSpDetailService.findBySelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
        if (selfHelpInvoiceSpDetailEntity == null) {
            selfHelpInvoiceSpDetailEntity = new SelfHelpInvoiceSpDetailEntity();
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceApplyProviderId(selfHelpInvoiceSpEntity.getId());
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
            selfHelpInvoiceSpDetailEntity.setInvoiceScanPictures(selfHelpInvoiceDetailInvoiceTaxDto.getInvoiceScanPictures());
            selfHelpInvoiceSpDetailEntity.setTaxScanPictures(selfHelpInvoiceDetailInvoiceTaxDto.getTaxScanPictures());
            selfHelpInvoiceSpDetailService.save(selfHelpInvoiceSpDetailEntity);
        } else {
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceApplyProviderId(selfHelpInvoiceSpEntity.getId());
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
            selfHelpInvoiceSpDetailEntity.setInvoiceScanPictures(selfHelpInvoiceDetailInvoiceTaxDto.getInvoiceScanPictures());
            selfHelpInvoiceSpDetailEntity.setTaxScanPictures(selfHelpInvoiceDetailInvoiceTaxDto.getTaxScanPictures());
            selfHelpInvoiceSpDetailService.updateById(selfHelpInvoiceSpDetailEntity);
        }

        return R.success("上传成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> fillExpressByProvider(ServiceProviderWorkerEntity serviceProviderWorkerEntity, SelfHelpInvoiceExpressDTO selfHelpInvoiceExpressDto) {

        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(selfHelpInvoiceExpressDto.getSelfHelpInvoiceId());
        if (selfHelpInvoiceEntity == null) {
            return R.fail("自助开票不存在");
        }

        if (!(SelfHelpInvoiceApplyState.PAID.equals(selfHelpInvoiceEntity.getCurrentState())) && !(SelfHelpInvoiceApplyState.INVOICED.equals(selfHelpInvoiceEntity.getCurrentState()))) {
            return R.fail("自助开票的开票状态有误");
        }

        //todo
        //1 判断自助开票-服务商的所有自助开票明细是否都已完成
        //1 判断自助开票的所有自助开票明细是否都已完成
        SelfHelpInvoiceSpEntity selfHelpInvoiceSpEntity = selfHelpInvoiceSpService.findByServiceProviderIdAndSelfHelpInvoiceId(serviceProviderWorkerEntity.getServiceProviderId(), selfHelpInvoiceEntity.getId());
        if (selfHelpInvoiceSpEntity == null) {
            return R.fail("自助开票明细不属于当前服务商");
        }

        //添加自助开票快递
        SelfHelpInvoiceExpressEntity selfHelpInvoiceExpressEntity = selfHelpInvoiceExpressService.findBySelfHelpInvoiceApplyProviderId(selfHelpInvoiceSpEntity.getId());
        if (selfHelpInvoiceExpressEntity == null) {
            selfHelpInvoiceExpressEntity = new SelfHelpInvoiceExpressEntity();
            selfHelpInvoiceExpressEntity.setSelfHelpInvoiceApplyProviderId(selfHelpInvoiceSpEntity.getId());
            selfHelpInvoiceExpressEntity.setExpressNo(selfHelpInvoiceExpressDto.getExpressSheetNo());
            selfHelpInvoiceExpressEntity.setExpressCompanyName(selfHelpInvoiceExpressDto.getExpressCompanyName());
            selfHelpInvoiceExpressService.save(selfHelpInvoiceExpressEntity);
        } else {
            selfHelpInvoiceExpressEntity.setSelfHelpInvoiceApplyProviderId(selfHelpInvoiceSpEntity.getId());
            selfHelpInvoiceExpressEntity.setExpressNo(selfHelpInvoiceExpressDto.getExpressSheetNo());
            selfHelpInvoiceExpressEntity.setExpressCompanyName(selfHelpInvoiceExpressDto.getExpressCompanyName());
            selfHelpInvoiceExpressService.save(selfHelpInvoiceExpressEntity);
        }

        //todo
        //自助开票-服务商设置为已开票状态

        return R.success("上传成功");
    }

    @Override
    public R<SelfHelpInvoiceExpressByEnterpriseProviderVO> getSelfHelfInvoiceExpressBySelfHelfInvoiceAndProvider(Long serviceProviderId, Long selfHelpInvoiceId) {

        SelfHelpInvoiceExpressByEnterpriseProviderVO selfHelpInvoiceExpressByEnterpriseProviderVO = baseMapper.getSelfHelfInvoiceExpressBySelfHelfInvoiceAndProvider(selfHelpInvoiceId, serviceProviderId);
        //根据快递公司，快递单号查询快递信息
        KdniaoTrackQueryUtil kdniaoTrackQueryUtil = new KdniaoTrackQueryUtil();
        String expressMessage = null;
        if (StringUtils.isNotBlank(selfHelpInvoiceExpressByEnterpriseProviderVO.getExpressCompanyName()) && StringUtils.isNotBlank(selfHelpInvoiceExpressByEnterpriseProviderVO.getExpressNo())) {
            try {
                expressMessage = kdniaoTrackQueryUtil.getOrderTracesByJson(selfHelpInvoiceExpressByEnterpriseProviderVO.getExpressCompanyName(), selfHelpInvoiceExpressByEnterpriseProviderVO.getExpressNo());
                Map<String,Object> maps = (Map) JSON.parse(expressMessage);
                Boolean success = (Boolean) maps.get("Success");
                if(success){
                    selfHelpInvoiceExpressByEnterpriseProviderVO.setExpressMessage(maps.get("Traces"));
                }else{
                    selfHelpInvoiceExpressByEnterpriseProviderVO.setExpressMessage("");
                }
            } catch (Exception e) {
                log.error("查询快递异常", e);
            }
        }
        return R.data(selfHelpInvoiceExpressByEnterpriseProviderVO);
    }

    @Override
    public R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long individualId, MakerType makerType) {
        return R.data(baseMapper.selfHelpInvoiceStatistics(individualId, makerType));
    }

    @Override
    public R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(IPage<SelfHelpInvoiceListVO> page, Long individualId, MakerType makerType) {

        List<SelfHelpInvoiceListVO> selfHelpInvoiceListVOs = baseMapper.selfHelpInvoiceList(individualId, makerType, page);
        BizType bizType = null;
        switch (makerType) {

            case INDIVIDUALENTERPRISE:
                IndividualEnterpriseEntity individualEnterpriseEntity = iUserClient.queryIndividualEnterpriseById(individualId);
                if (individualEnterpriseEntity != null) {
                    bizType = individualEnterpriseEntity.getBizType();
                }
                break;

            case INDIVIDUALBUSINESS:
                IndividualBusinessEntity individualBusinessEntity = iUserClient.queryIndividualBusinessById(individualId);
                if (individualBusinessEntity != null) {
                    bizType = individualBusinessEntity.getBizType();
                }
                break;

            default:
                break;
        }

        if (bizType != null && CollectionUtil.isNotEmpty(selfHelpInvoiceListVOs)) {
            BizType finalBizType = bizType;
            selfHelpInvoiceListVOs.forEach(selfHelpInvoiceListVO -> selfHelpInvoiceListVO.setBizType(finalBizType));
        }

        return R.data(page.setRecords(selfHelpInvoiceListVOs));
    }

    @Override
    public R findEnterpriseCrowdSourcing(Long enterpriseId, String serviceProviderName, IPage<SelfHelpInvoiceCrowdSourcingVO> page) {
        return R.data(page.setRecords(baseMapper.findEnterpriseCrowdSourcing(enterpriseId, serviceProviderName, page)));
    }

    @Override
    public R findDetailCrowdSourcing(Long selfHelpInvoiceId) {
        //查询众包详情
        SelfHelpInvoiceCrowdSourcingVO detailCrowdSourcing = baseMapper.findDetailCrowdSourcing(selfHelpInvoiceId);
        Map map = new HashMap();
        map.put("detailCrowdSourcing", detailCrowdSourcing);
        KdniaoTrackQueryUtil kdniaoTrackQueryUtil = new KdniaoTrackQueryUtil();
        //查询快递
        String orderTracesByJson = "";
        try {
            orderTracesByJson = kdniaoTrackQueryUtil.getOrderTracesByJson(detailCrowdSourcing.getExpressCompanyName(), detailCrowdSourcing.getExpressSheetNo());
            Map<String,Object> maps = (Map) JSON.parse(orderTracesByJson);
            Boolean success = (Boolean) maps.get("Success");
            if(success){
                map.put("orderTracesByJson", maps.get("Traces"));
            }else{
                map.put("orderTracesByJson", "");
            }
        } catch (Exception e) {
            log.info("快鸟接口访问失败");
            map.put("orderTracesByJson", "");
        }
        return R.data(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> audit(Long serviceProviderId, Long selfHelpInvoiceId, SelfHelpInvoiceSpApplyState applyState) {

        if (!(SelfHelpInvoiceSpApplyState.RECALLED.equals(applyState)) && !(SelfHelpInvoiceSpApplyState.SUBMITTED.equals(applyState))) {
            return R.fail("审核状态有误");
        }

        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(selfHelpInvoiceId);
        if (selfHelpInvoiceEntity == null) {
            return R.fail("自助开票不存在");
        }

        SelfHelpInvoiceSpEntity selfHelpInvoiceSpEntity = selfHelpInvoiceSpService.findBySelfHelpInvoiceIdAndAuditing(selfHelpInvoiceId);
        if (selfHelpInvoiceSpEntity == null) {
            return R.fail("审核中的自助开票不存在");
        }

        if (!(selfHelpInvoiceSpEntity.getServiceProviderId().equals(serviceProviderId))) {
            return R.fail("自助开票不属于当前服务商");
        }

        selfHelpInvoiceSpEntity.setApplyState(applyState);
        selfHelpInvoiceSpService.save(selfHelpInvoiceSpEntity);

        return R.success("审核成功");
    }

    @Override
    public R<YearTradeVO> queryCrowdYearTradeByEnterprise(Long enterpriseId) {
        return R.data(baseMapper.queryCrowdYearTradeByEnterprise(enterpriseId));
    }

    @Override
    public R<YearTradeVO> queryCrowdYearTradeByServiceProvider(Long serviceProviderId) {
        return R.data(baseMapper.queryCrowdYearTradeByServiceProvider(serviceProviderId));
    }

    @Override
    public R<MonthTradeVO> queryCrowdMonthTradeByEnterprise(Long enterpriseId) {
        return R.data(baseMapper.queryCrowdMonthTradeByEnterprise(enterpriseId));
    }

    @Override
    public R<MonthTradeVO> queryCrowdMonthTradeByServiceProvider(Long serviceProviderId) {
        return R.data(baseMapper.queryCrowdMonthTradeByServiceProvider(serviceProviderId));
    }

    @Override
    public R<WeekTradeVO> queryCrowdWeekTradeByEnterprise(Long enterpriseId) {
        return R.data(baseMapper.queryCrowdWeekTradeByEnterprise(enterpriseId));
    }

    @Override
    public R<WeekTradeVO> queryCrowdWeekTradeByServiceProvider(Long serviceProviderId) {
        return R.data(baseMapper.queryCrowdWeekTradeByServiceProvider(serviceProviderId));
    }

    @Override
    public R<DayTradeVO> queryCrowdDayTradeByEnterprise(Long enterpriseId) {
        return R.data(baseMapper.queryCrowdDayTradeByEnterprise(enterpriseId));
    }

    @Override
    public R<DayTradeVO> queryCrowdDayTradeByServiceProvider(Long serviceProviderId) {
        return R.data(baseMapper.queryCrowdDayTradeByServiceProvider(serviceProviderId));
    }

    @Override
    public R getAdminMakerTypeSelfHelpInvoice(String enterpriseName, String startTime, String endTime, MakerType makerType, IPage<SelfHelpInvoiceAdminVO> page) {
        return R.data(page.setRecords(baseMapper.getAdminMakerTypeSelfHelpInvoice(enterpriseName, startTime, endTime, makerType, page)));
    }

    @Override
    public R getMakerTypeSelfHelpInvoiceDetails(Long selfHelpInvoiceId) {
        Map map = new HashMap();
        SelfHelpInvoiceAdminDetailVO makerTypeSelfHelpInvoiceDetails = baseMapper.getMakerTypeSelfHelpInvoiceDetails(selfHelpInvoiceId);
        List<SelfHelpInvoiceDetailAdminVO> selfHelpInvoiceDetailAdminVOS = selfHelpInvoiceDetailService.getSelfHelpInvoiceIdAll(selfHelpInvoiceId);
        map.put("makerTypeSelfHelpInvoiceDetails", makerTypeSelfHelpInvoiceDetails);
        map.put("selfHelpInvoiceDetailAdminVOS", selfHelpInvoiceDetailAdminVOS);
        String orderTracesByJson = "";
        if (selfHelpInvoiceDetailAdminVOS.size() > 0) {
            String expressCompanyName = selfHelpInvoiceDetailAdminVOS.get(0).getExpressCompanyName();
            String expressNo = selfHelpInvoiceDetailAdminVOS.get(0).getExpressNo();
            try {
                KdniaoTrackQueryUtil kdniaoTrackQueryUtil = new KdniaoTrackQueryUtil();
                orderTracesByJson = kdniaoTrackQueryUtil.getOrderTracesByJson(expressCompanyName, expressNo);
                Map<String,Object> maps = (Map) JSON.parse(orderTracesByJson);
                Boolean success = (Boolean) maps.get("Success");
                if(success){
                    map.put("orderTracesByJson", maps.get("Traces"));
                }else{
                    map.put("orderTracesByJson", "");
                }
            } catch (Exception e) {
                map.put("orderTracesByJson", orderTracesByJson);
            }
        }
        return R.data(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R toExamineSelfHelpInvoice(ToExamineSelfHelpInvoiceDTO toExamineSelfHelpInvoiceDto) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(toExamineSelfHelpInvoiceDto.getSelfHelpInvoiceId());
        if (null == selfHelpInvoiceEntity) {
            return R.fail("自助开票不存在");
        }

        selfHelpInvoiceEntity.setTotlChargeMoneyNum(toExamineSelfHelpInvoiceDto.getTotlChargeMoneyNum());
        selfHelpInvoiceEntity.setServiceRate(toExamineSelfHelpInvoiceDto.getServiceRate());
        selfHelpInvoiceEntity.setServiceTax(toExamineSelfHelpInvoiceDto.getServiceTax());
        selfHelpInvoiceEntity.setServiceAndTaxMoney(toExamineSelfHelpInvoiceDto.getServiceandTaxMoney());
        selfHelpInvoiceEntity.setServiceInvoiceFee(toExamineSelfHelpInvoiceDto.getServiceInvoiceFee());
        selfHelpInvoiceEntity.setTotalPayProviderFee(toExamineSelfHelpInvoiceDto.getTotalPayProviderFee());
        selfHelpInvoiceEntity.setIdendityConfirmFee(toExamineSelfHelpInvoiceDto.getIdendityConfirmFee());
        selfHelpInvoiceEntity.setServiceFee(toExamineSelfHelpInvoiceDto.getServiceFee());
        selfHelpInvoiceEntity.setCurrentState(SelfHelpInvoiceApplyState.TOPAY);
        updateById(selfHelpInvoiceEntity);

        List<SelfHelpInvoiceDetailVO> selfHelpInvoiceId = selfHelpInvoiceDetailService.getSelfHelpInvoiceId(toExamineSelfHelpInvoiceDto.getSelfHelpInvoiceId());
        for (SelfHelpInvoiceDetailVO selfHelpInvoiceDetailVO : selfHelpInvoiceId) {
            SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = BeanUtil.copy(selfHelpInvoiceDetailVO, SelfHelpInvoiceDetailEntity.class);
            BigDecimal subtract = toExamineSelfHelpInvoiceDto.getServiceRate().subtract(selfHelpInvoiceDetailEntity.getChargeMoneyNum());
            selfHelpInvoiceDetailEntity.setPayProviderFee(subtract);
            selfHelpInvoiceDetailEntity.setInvoicePrintState(InvoicePrintState.APPLYING);
            selfHelpInvoiceDetailService.saveOrUpdate(selfHelpInvoiceDetailEntity);
        }
        SelfHelpInvoiceAccountEntity selfHelpInvoiceAccountEntity = new SelfHelpInvoiceAccountEntity();
        selfHelpInvoiceAccountEntity.setAccountBank(toExamineSelfHelpInvoiceDto.getAccountBank());
        selfHelpInvoiceAccountEntity.setAccountName(toExamineSelfHelpInvoiceDto.getAccountName());
        selfHelpInvoiceAccountEntity.setAccountNo(toExamineSelfHelpInvoiceDto.getAccountNo());
        selfHelpInvoiceAccountEntity.setBoolDefault(true);
        selfHelpInvoiceAccountService.save(selfHelpInvoiceAccountEntity);
        SelfHelpInvoiceFeeEntity selfHelpInvoiceFeeEntity = new SelfHelpInvoiceFeeEntity();
        selfHelpInvoiceFeeEntity.setSelfHelpInvoiceId(toExamineSelfHelpInvoiceDto.getSelfHelpInvoiceId());
        selfHelpInvoiceFeeEntity.setGivePriceDate(new Date());
        selfHelpInvoiceFeeEntity.setTotalTaxFee(toExamineSelfHelpInvoiceDto.getServiceTax());
        selfHelpInvoiceFeeEntity.setBasicTaxFee(toExamineSelfHelpInvoiceDto.getServiceandTaxMoney());
        selfHelpInvoiceFeeEntity.setBasicTaxFeeRate(toExamineSelfHelpInvoiceDto.getServiceRate());
        selfHelpInvoiceFeeEntity.setInvoiceFee(toExamineSelfHelpInvoiceDto.getServiceInvoiceFee());
        selfHelpInvoiceFeeEntity.setIdentifyFee(toExamineSelfHelpInvoiceDto.getIdendityConfirmFee());
        selfHelpInvoiceFeeEntity.setHandPayAccountId(selfHelpInvoiceAccountEntity.getId());
        selfHelpInvoiceFeeService.save(selfHelpInvoiceFeeEntity);
        return R.success("审核成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R matchServiceProvider(Long selfHelpInvoiceId, Long selfHelpInvoiceFeeId, Long serviceProviderId, String payCertificate) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(serviceProviderId);
        if (null == selfHelpInvoiceEntity) {
            return R.fail("自主开票不存在");
        }

        if (selfHelpInvoiceEntity.getCurrentState().equals("TOPAY")) {
            return R.fail("自助开票状态有误");
        }

        SelfHelpInvoiceFeeEntity selfHelpInvoiceFeeEntity = selfHelpInvoiceFeeService.getById(selfHelpInvoiceFeeId);
        selfHelpInvoiceFeeEntity.setPayCertificate(payCertificate);
        selfHelpInvoiceFeeService.updateById(selfHelpInvoiceFeeEntity);

        SelfHelpInvoiceSpEntity selfHelpInvoiceSpEntity = new SelfHelpInvoiceSpEntity();
        selfHelpInvoiceSpEntity.setSelfHelpInvoiceId(selfHelpInvoiceId);
        selfHelpInvoiceSpEntity.setServiceProviderId(serviceProviderId);
        selfHelpInvoiceSpEntity.setApplyState(SelfHelpInvoiceSpApplyState.ALLOCATED);
        selfHelpInvoiceSpEntity.setChargeMoneyNum(selfHelpInvoiceEntity.getTotlChargeMoneyNum());
        selfHelpInvoiceSpEntity.setValueMoneyNum(selfHelpInvoiceEntity.getTotlChargeMoneyNum());
        selfHelpInvoiceSpEntity.setServiceRate(selfHelpInvoiceEntity.getServiceRate());
        selfHelpInvoiceSpEntity.setServiceAndTaxMoney(selfHelpInvoiceEntity.getServiceAndTaxMoney());
        selfHelpInvoiceSpEntity.setServiceInvoiceFee(selfHelpInvoiceEntity.getServiceInvoiceFee());
        selfHelpInvoiceSpEntity.setIdendityConfirmFee(selfHelpInvoiceFeeEntity.getIdentifyFee());
        selfHelpInvoiceSpEntity.setPayTotalNum(selfHelpInvoiceEntity.getTotlChargeMoneyNum());
        selfHelpInvoiceSpEntity.setAddressType(AddressType.TOCUSTOMER);
        selfHelpInvoiceSpService.save(selfHelpInvoiceSpEntity);

        List<SelfHelpInvoiceDetailVO> selfHelpInvoiceIds = selfHelpInvoiceDetailService.getSelfHelpInvoiceId(selfHelpInvoiceId);
        for (SelfHelpInvoiceDetailVO selfHelpInvoiceDetailVO : selfHelpInvoiceIds) {
            SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = BeanUtil.copy(selfHelpInvoiceDetailVO, SelfHelpInvoiceDetailEntity.class);
            SelfHelpInvoiceSpDetailEntity selfHelpInvoiceSpDetailEntity = new SelfHelpInvoiceSpDetailEntity();
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceApplyProviderId(selfHelpInvoiceSpEntity.getId());
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
            selfHelpInvoiceSpDetailService.save(selfHelpInvoiceSpDetailEntity);
        }
        return R.success("操作成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R uploadAdminExpress(Long selfHelpInvoiceId, Long serviceProviderId, String expressNo, String expressCompanyName) {
        SelfHelpInvoiceSpEntity selfHelpInvoiceSpEntity = selfHelpInvoiceSpService.findByServiceProviderIdAndSelfHelpInvoiceId(selfHelpInvoiceId, serviceProviderId);
        if (null == selfHelpInvoiceSpEntity) {
            return R.data("自助开票-服务商记录不存在");
        }

        if (SelfHelpInvoiceSpApplyState.INVOICED.equals(selfHelpInvoiceSpEntity.getApplyState())){
            return R.data("自助开票-服务商记录已开票结束");
        }

        SelfHelpInvoiceExpressEntity selfHelpInvoiceExpressEntity = new SelfHelpInvoiceExpressEntity();
        selfHelpInvoiceExpressEntity.setSelfHelpInvoiceApplyProviderId(selfHelpInvoiceSpEntity.getId());
        selfHelpInvoiceExpressEntity.setExpressNo(expressNo);
        selfHelpInvoiceExpressEntity.setExpressCompanyName(expressCompanyName);
        selfHelpInvoiceExpressService.save(selfHelpInvoiceExpressEntity);
        return R.success("成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R uploadAdminInvoice(Long selfHelpInvoiceApplyProviderDetailId, String invoiceScanPictures, String taxScanPictures) {
        SelfHelpInvoiceSpDetailEntity selfHelpInvoiceSpDetailEntity = selfHelpInvoiceSpDetailService.getById(selfHelpInvoiceApplyProviderDetailId);
        if (selfHelpInvoiceSpDetailEntity == null){
            return R.fail("服务商开票明细不存在");
        }

        selfHelpInvoiceSpDetailEntity.setTaxScanPictures(taxScanPictures);
        selfHelpInvoiceSpDetailEntity.setInvoiceScanPictures(invoiceScanPictures);
        selfHelpInvoiceSpDetailService.updateById(selfHelpInvoiceSpDetailEntity);
        return R.success("上传成功");
    }

    @Override
    public R<IPage<SelfHelpInvoiceSerProVO>> querySelfHelpInvoiceList(Long serviceProviderId, String keyword, IPage<SelfHelpInvoiceSerProVO> page) {
        return R.data(page.setRecords(baseMapper.querySelfHelpInvoiceList(serviceProviderId, keyword, page)));
    }

    @Override
    public R<IPage<SelfHelpInvoiceDetailProviderVO>> querySelfHelpInvoicePeopleList(Long selfHelpvoiceId, IPage<SelfHelpInvoiceDetailProviderVO> page) {
        return R.data(page.setRecords(baseMapper.querySelfHelpInvoicePeopleList(selfHelpvoiceId, page)));
    }

}
