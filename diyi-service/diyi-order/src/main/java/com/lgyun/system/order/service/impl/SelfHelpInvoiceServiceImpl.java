package com.lgyun.system.order.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.CollectionUtil;
import com.lgyun.common.tool.KdniaoTrackQueryUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.*;
import com.lgyun.system.order.entity.*;
import com.lgyun.system.order.excel.InvoiceListExcel;
import com.lgyun.system.order.excel.InvoiceListListener;
import com.lgyun.system.order.mapper.SelfHelpInvoiceMapper;
import com.lgyun.system.order.service.*;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.feign.IUserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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
    private final ISelfHelpInvoiceFeeService selfHelpInvoiceFeeService;
    private final ISelfHelpInvoiceExpressService selfHelpInvoiceExpressService;
    private final ISelfHelpInvoiceAccountService selfHelpInvoiceAccountService;
    private final ISelfHelpInvoiceSpDetailService selfHelpInvoiceSpDetailService;
    private final IAddressService addressService;
    private final ISelfHelpInvoicePersonService selfHelpInvoicePersonService;
    private final ISelfHelpInvoiceApplyService selfHelpInvoiceApplyService;
    private final IAcceptPaysheetCsService acceptPaysheetCsService;



    @Autowired
    @Lazy
    private ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;

//    @Override
//    public R<IPage<SelfHelpInvoiceListByEnterpriseVO>> getSelfHelfInvoicesByEnterprise(Long enterpriseId, MakerType makerType, SelfHelpInvoicesByEnterpriseDTO selfHelpInvoicesByEnterpriseDto, IPage<SelfHelpInvoiceListByEnterpriseVO> page) {
//
//        if (selfHelpInvoicesByEnterpriseDto.getBeginDate() != null && selfHelpInvoicesByEnterpriseDto.getEndDate() != null) {
//            if (selfHelpInvoicesByEnterpriseDto.getBeginDate().after(selfHelpInvoicesByEnterpriseDto.getEndDate())) {
//                return R.fail("开始时间不能大于结束时间");
//            }
//        }
//
//        return R.data(page.setRecords(baseMapper.getSelfHelfInvoicesByEnterprise(enterpriseId, makerType, selfHelpInvoicesByEnterpriseDto, page)));
//    }

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
                    Map<String, Object> maps = (Map) JSON.parse(expressMessage);
                    Boolean success = (Boolean) maps.get("Success");
                    if (success) {
                        selfHelpInvoiceExpressByEnterpriseVO.setExpressMessage(maps.get("Traces"));
                    } else {
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
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
            selfHelpInvoiceSpDetailEntity.setInvoiceScanPictures(selfHelpInvoiceDetailInvoiceTaxDto.getInvoiceScanPictures());
            selfHelpInvoiceSpDetailEntity.setTaxScanPictures(selfHelpInvoiceDetailInvoiceTaxDto.getTaxScanPictures());
            selfHelpInvoiceSpDetailService.save(selfHelpInvoiceSpDetailEntity);
        } else {
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
                Map<String, Object> maps = (Map) JSON.parse(expressMessage);
                Boolean success = (Boolean) maps.get("Success");
                if (success) {
                    selfHelpInvoiceExpressByEnterpriseProviderVO.setExpressMessage(maps.get("Traces"));
                } else {
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
            Map<String, Object> maps = (Map) JSON.parse(orderTracesByJson);
            Boolean success = (Boolean) maps.get("Success");
            if (success) {
                map.put("orderTracesByJson", maps.get("Traces"));
            } else {
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
    public R<List<TradeVO>> queryCrowdTrade(Long enterpriseId, Long serviceProviderId, Long relBureauId, TimeType timeType, Date beginDate, Date endDate) {

        if (TimeType.PERIOD.equals(timeType) && (beginDate == null || endDate == null)){
            return R.fail("请选择开始时间和结束时间");
        }

        return R.data(baseMapper.queryCrowdTrade(enterpriseId, serviceProviderId, relBureauId, timeType.getValue(), beginDate, endDate));
    }

    @Override
    public R<IPage<TotalCrowdTradeListVO>> queryRelBureauCrowdList(Long relBureauId, IPage<TotalCrowdTradeListVO> page) {
        return R.data(page.setRecords(baseMapper.queryRelBureauCrowdList(relBureauId, page)));
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
                Map<String, Object> maps = (Map) JSON.parse(orderTracesByJson);
                Boolean success = (Boolean) maps.get("Success");
                if (success) {
                    map.put("orderTracesByJson", maps.get("Traces"));
                } else {
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
        if (MakerType.NATURALPERSON.equals(toExamineSelfHelpInvoiceDto.getMakerType()) && null == toExamineSelfHelpInvoiceDto.getSelfHelpInvoiceId()){
            return R.fail("请选择服务商");
        }

        if(AuditState.EDITING.equals(toExamineSelfHelpInvoiceDto.getAuditStatu())){
            return R.fail("审核状态错误");
        }
        if(AuditState.APPROVED.equals(toExamineSelfHelpInvoiceDto.getAuditStatu())){
            selfHelpInvoiceEntity.setServiceRate(toExamineSelfHelpInvoiceDto.getServiceRate());
            selfHelpInvoiceEntity.setServiceTax(toExamineSelfHelpInvoiceDto.getServiceTax());
            selfHelpInvoiceEntity.setServiceAndTaxMoney(toExamineSelfHelpInvoiceDto.getServiceandTaxMoney());
            selfHelpInvoiceEntity.setServiceInvoiceFee(toExamineSelfHelpInvoiceDto.getServiceInvoiceFee());
            selfHelpInvoiceEntity.setTotalPayProviderFee(toExamineSelfHelpInvoiceDto.getTotalPayProviderFee());
            selfHelpInvoiceEntity.setIdendityConfirmFee(toExamineSelfHelpInvoiceDto.getIdendityConfirmFee());
            selfHelpInvoiceEntity.setServiceFee(toExamineSelfHelpInvoiceDto.getServiceFee());
            selfHelpInvoiceEntity.setCurrentState(SelfHelpInvoiceApplyState.TOPAY);
            SelfHelpInvoiceSpEntity selfHelpInvoiceSpEntity = selfHelpInvoiceSpService.findBySelfHelpInvoiceIdAndAuditing(toExamineSelfHelpInvoiceDto.getSelfHelpInvoiceId());
            if(null == selfHelpInvoiceSpEntity){
                if(MakerType.NATURALPERSON.equals(toExamineSelfHelpInvoiceDto.getMakerType())){
                    selfHelpInvoiceSpEntity = new SelfHelpInvoiceSpEntity();
                    selfHelpInvoiceSpEntity.setServiceProviderId(toExamineSelfHelpInvoiceDto.getServiceProviderId());
                    selfHelpInvoiceSpEntity.setApplyState(SelfHelpInvoiceSpApplyState.ALLOCATED);
                    selfHelpInvoiceSpEntity.setServiceRate(selfHelpInvoiceEntity.getServiceRate());
                    selfHelpInvoiceSpEntity.setAddressType(AddressType.TOCUSTOMER);
                    selfHelpInvoiceSpEntity.setChargeMoneyNum(toExamineSelfHelpInvoiceDto.getTotalPayProviderFee());
                    selfHelpInvoiceSpEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
                    selfHelpInvoiceSpEntity.setValueMoneyNum(toExamineSelfHelpInvoiceDto.getTotalPayProviderFee());
                    selfHelpInvoiceSpEntity.setServiceRate(toExamineSelfHelpInvoiceDto.getServiceRate());
                    selfHelpInvoiceSpEntity.setServiceAndTaxMoney(toExamineSelfHelpInvoiceDto.getServiceTax());
                    selfHelpInvoiceSpEntity.setServiceInvoiceFee(toExamineSelfHelpInvoiceDto.getServiceInvoiceFee());
                    selfHelpInvoiceSpEntity.setIdendityConfirmFee(toExamineSelfHelpInvoiceDto.getIdendityConfirmFee());
                    selfHelpInvoiceSpEntity.setPayTotalNum(toExamineSelfHelpInvoiceDto.getTotalPayProviderFee());
                    List<SelfHelpInvoiceDetailVO> selfHelpInvoiceIds = selfHelpInvoiceDetailService.getSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
                    for (SelfHelpInvoiceDetailVO selfHelpInvoiceDetailVO : selfHelpInvoiceIds) {
                        SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = BeanUtil.copy(selfHelpInvoiceDetailVO, SelfHelpInvoiceDetailEntity.class);
                        SelfHelpInvoiceSpDetailEntity selfHelpInvoiceSpDetailEntity = new SelfHelpInvoiceSpDetailEntity();
                        selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
                        selfHelpInvoiceSpDetailService.save(selfHelpInvoiceSpDetailEntity);
                    }
                }else {
                    return R.fail("数据格式错误！！！");
                }
            }else{
                selfHelpInvoiceSpEntity.setChargeMoneyNum(toExamineSelfHelpInvoiceDto.getTotalPayProviderFee());
                selfHelpInvoiceSpEntity.setValueMoneyNum(toExamineSelfHelpInvoiceDto.getTotalPayProviderFee());
                selfHelpInvoiceSpEntity.setServiceRate(toExamineSelfHelpInvoiceDto.getServiceRate());
                selfHelpInvoiceSpEntity.setServiceAndTaxMoney(toExamineSelfHelpInvoiceDto.getServiceTax());
                selfHelpInvoiceSpEntity.setServiceInvoiceFee(toExamineSelfHelpInvoiceDto.getServiceInvoiceFee());
                selfHelpInvoiceSpEntity.setIdendityConfirmFee(toExamineSelfHelpInvoiceDto.getIdendityConfirmFee());
                selfHelpInvoiceSpEntity.setPayTotalNum(toExamineSelfHelpInvoiceDto.getTotalPayProviderFee());
                selfHelpInvoiceSpService.saveOrUpdate(selfHelpInvoiceSpEntity);
            }
            updateById(selfHelpInvoiceEntity);

            List<SelfHelpInvoiceDetailVO> selfHelpInvoiceId = selfHelpInvoiceDetailService.getSelfHelpInvoiceId(toExamineSelfHelpInvoiceDto.getSelfHelpInvoiceId());
            for (SelfHelpInvoiceDetailVO selfHelpInvoiceDetailVO : selfHelpInvoiceId) {
                SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = BeanUtil.copy(selfHelpInvoiceDetailVO, SelfHelpInvoiceDetailEntity.class);
                BigDecimal subtract = toExamineSelfHelpInvoiceDto.getServiceRate().subtract(selfHelpInvoiceDetailEntity.getChargeMoneyNum());
                selfHelpInvoiceDetailEntity.setPayProviderFee(subtract);
                selfHelpInvoiceDetailEntity.setInvoicePrintState(SelfHelpInvoiceApplyState.TOPAY);
                selfHelpInvoiceDetailService.saveOrUpdate(selfHelpInvoiceDetailEntity);
            }
            SelfHelpInvoiceApplyEntity selfHelpInvoiceApplyEntity = selfHelpInvoiceApplyService.getBySelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
            selfHelpInvoiceApplyEntity.setApplyState(SelfHelpInvoiceApplyState.TOPAY);
            selfHelpInvoiceApplyEntity.setAuditDesc(toExamineSelfHelpInvoiceDto.getAuditDesc());
            selfHelpInvoiceApplyService.saveOrUpdate(selfHelpInvoiceApplyEntity);

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
        }
        if(AuditState.REJECTED.equals(toExamineSelfHelpInvoiceDto.getAuditStatu())){
            if(StringUtils.isBlank(toExamineSelfHelpInvoiceDto.getAuditDesc())){
                return R.fail("审核说明不能为空");
            }
            SelfHelpInvoiceApplyEntity selfHelpInvoiceApplyEntity = selfHelpInvoiceApplyService.getBySelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
            selfHelpInvoiceApplyEntity.setApplyState(SelfHelpInvoiceApplyState.REJECTED);
            selfHelpInvoiceApplyEntity.setAuditDesc(toExamineSelfHelpInvoiceDto.getAuditDesc());
            selfHelpInvoiceApplyService.saveOrUpdate(selfHelpInvoiceApplyEntity);
        }
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
        selfHelpInvoiceSpEntity.setServiceRate(selfHelpInvoiceEntity.getServiceRate());
        selfHelpInvoiceSpEntity.setServiceAndTaxMoney(selfHelpInvoiceEntity.getServiceAndTaxMoney());
        selfHelpInvoiceSpEntity.setServiceInvoiceFee(selfHelpInvoiceEntity.getServiceInvoiceFee());
        selfHelpInvoiceSpEntity.setAddressType(AddressType.TOCUSTOMER);
        selfHelpInvoiceSpService.save(selfHelpInvoiceSpEntity);

        List<SelfHelpInvoiceDetailVO> selfHelpInvoiceIds = selfHelpInvoiceDetailService.getSelfHelpInvoiceId(selfHelpInvoiceId);
        for (SelfHelpInvoiceDetailVO selfHelpInvoiceDetailVO : selfHelpInvoiceIds) {
            SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = BeanUtil.copy(selfHelpInvoiceDetailVO, SelfHelpInvoiceDetailEntity.class);
            SelfHelpInvoiceSpDetailEntity selfHelpInvoiceSpDetailEntity = new SelfHelpInvoiceSpDetailEntity();
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
            selfHelpInvoiceSpDetailService.save(selfHelpInvoiceSpDetailEntity);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R uploadAdminExpress(Long selfHelpInvoiceId, Long serviceProviderId, String expressNo, String expressCompanyName) {
        SelfHelpInvoiceSpEntity selfHelpInvoiceSpEntity = selfHelpInvoiceSpService.findByServiceProviderIdAndSelfHelpInvoiceId(selfHelpInvoiceId, serviceProviderId);
        if (null == selfHelpInvoiceSpEntity) {
            return R.data("自助开票-服务商记录不存在");
        }

        if (SelfHelpInvoiceSpApplyState.INVOICED.equals(selfHelpInvoiceSpEntity.getApplyState())) {
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
        if (selfHelpInvoiceSpDetailEntity == null) {
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



    @Override
    public R naturalPersonSubmitForm(ObjectType objectType, Long objectId,String listFile,Long serviceProviderId,InvoiceCategory invoiceCategory,MakerType makerType, CrowdSourcingPayType payType, String invoiceType, Long addressId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        InputStream inputStream = new URL(listFile).openStream();
        InvoiceListListener invoiceListListener = new InvoiceListListener();
        ExcelReader excelReader = EasyExcelFactory.read(inputStream, InvoiceListExcel.class, invoiceListListener).headRowNumber(1).build();
        excelReader.readAll();
        List<InvoiceListExcel> invoiceListExcels = invoiceListListener.getList();
        excelReader.finish();
        for(InvoiceListExcel invoiceListExcel : invoiceListExcels){
            EnterpriseEntity enterpriseEntity = iUserClient.queryEnterpriseByName(invoiceListExcel.getPayer());
            if(null == enterpriseEntity){
                return R.fail("购买方，必须是平台商户会员");
            }

            if(MakerType.NATURALPERSON.equals(makerType)){
                MakerEntity makerEntity = iUserClient.queryMakerByPhoneNumber(invoiceListExcel.getPhoneNumber());
                SelfHelpInvoicePersonEntity selfHelpInvoicePersonEntity = selfHelpInvoicePersonService.findCardNo(invoiceListExcel.getIdcardNo());
                if(null != makerEntity){
                    invoiceListExcel.setPositiveIdCard(makerEntity.getIdcardPic());
                    invoiceListExcel.setBackIdCard(makerEntity.getIdcardPicBack());
                    AgreementEntity agreementEntity = iUserClient.queryEntMakSupplementaryAgreement(makerEntity.getId(), enterpriseEntity.getId());
                    invoiceListExcel.setBusinessContract(null != agreementEntity && null != agreementEntity.getPaperAgreementUrl() ? agreementEntity.getPaperAgreementUrl() : null);
                }else if(null != selfHelpInvoicePersonEntity){
                    invoiceListExcel.setPositiveIdCard(selfHelpInvoicePersonEntity.getIdcardPic());
                    invoiceListExcel.setBackIdCard(selfHelpInvoicePersonEntity.getIdcardPicBack());
                }else{
                    invoiceListExcel.setPositiveIdCard(null);
                    invoiceListExcel.setBackIdCard(null);
                }
            }
            if(MakerType.INDIVIDUALENTERPRISE.equals(makerType)){
                MakerEntity makerEntity = iUserClient.queryMakerByPhoneNumber(invoiceListExcel.getPhoneNumber());

                IndividualBusinessEntity individualBusinessEntity = iUserClient.queryIndividualBusinessByMakerIdAndIbtaxNo(makerEntity.getId(), invoiceListExcel.getAloneSocialCreditCode());
                if(null == individualBusinessEntity){
                    return R.success("个独必须是平台存在的个独"+invoiceListExcel.getAloneSocialCreditCode());
                }
                Long serviceProviderId1 = individualBusinessEntity.getServiceProviderId();
                if(serviceProviderId1 != serviceProviderId){
                    return R.success("个独必须是属于同一个服务商的"+invoiceListExcel.getAloneSocialCreditCode());
                }
                    invoiceListExcel.setPositiveIdCard(makerEntity.getIdcardPic());
                    invoiceListExcel.setBackIdCard(makerEntity.getIdcardPicBack());
                    AgreementEntity agreementEntity = iUserClient.queryEntMakSupplementaryAgreement(makerEntity.getId(), enterpriseEntity.getId());
                    invoiceListExcel.setBusinessContract(null != agreementEntity && null != agreementEntity.getPaperAgreementUrl() ? agreementEntity.getPaperAgreementUrl() : null);

            }
            if(MakerType.INDIVIDUALBUSINESS.equals(makerType)){
                MakerEntity makerEntity = iUserClient.queryMakerByPhoneNumber(invoiceListExcel.getPhoneNumber());
                IndividualEnterpriseEntity individualEnterpriseEntity = iUserClient.queryIndividualEnterpriseByMakerIdAndIbtaxNo(makerEntity.getId(), invoiceListExcel.getSocialCreditCode());
                if(null == individualEnterpriseEntity){
                    return R.success("个体户必须是平台存在的个独"+invoiceListExcel.getAloneSocialCreditCode());
                }
                Long serviceProviderId1 = individualEnterpriseEntity.getServiceProviderId();
                if(serviceProviderId1 != serviceProviderId){
                    return R.success("个体户必须是属于同一个服务商的"+invoiceListExcel.getAloneSocialCreditCode());
                }
                    invoiceListExcel.setPositiveIdCard(makerEntity.getIdcardPic());
                    invoiceListExcel.setBackIdCard(makerEntity.getIdcardPicBack());
                    AgreementEntity agreementEntity = iUserClient.queryEntMakSupplementaryAgreement(makerEntity.getId(), enterpriseEntity.getId());
                    invoiceListExcel.setBusinessContract(null != agreementEntity && null != agreementEntity.getPaperAgreementUrl() ? agreementEntity.getPaperAgreementUrl() : null);

            }

        }
        Map<String, List<InvoiceListExcel>> collect = invoiceListExcels.stream().collect(Collectors.groupingBy(InvoiceListExcel::getPayer));
        if(ObjectType.MAKERPEOPLE.equals(objectType)){
            MakerEntity makerEntity = iUserClient.queryMakerById(objectId);
            map.put("makerName",makerEntity.getName());
            map.put("enterpriseName","");
        }
        if(ObjectType.ENTERPRISEPEOPLE.equals(objectType)){
            EnterpriseEntity enterpriseEntity = iUserClient.queryEnterpriseById(objectId);
            map.put("enterpriseName",enterpriseEntity.getEnterpriseName());
            map.put("makerName","");
        }


        //商户名称

        map.put("collect",collect);

        map.put("listFile",listFile);

        //众包支付模型
        map.put("payType",payType);
        //开票类目
        map.put("invoiceType",invoiceType);
        //状态
        map.put("state", "编辑中");
        AddressEntity addressEntity = addressService.getById(addressId);
        map.put("addressId",addressId);
        map.put("addressName",null != addressEntity && null != addressEntity.getAddressName() ? addressEntity.getAddressName() : null);
        map.put("addressPhone",null != addressEntity && null != addressEntity.getAddressPhone() ? addressEntity.getAddressPhone() : null);
        map.put("province",null != addressEntity && null != addressEntity.getProvince() ? addressEntity.getProvince() : null);
        map.put("city",null != addressEntity && null != addressEntity.getCity() ? addressEntity.getCity() : null);
        map.put("area",null != addressEntity && null != addressEntity.getArea() ? addressEntity.getArea() : null);
        map.put("detailedAddress",null != addressEntity && null != addressEntity.getDetailedAddress() ? addressEntity.getDetailedAddress() : null);
        return R.data(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R naturalPersonConfirmSubmit(ObjectType objectType, Long objectId, NaturalPersonConfirmSubmitDTO naturalPersonConfirmSubmitDto) {
        if(!MakerType.NATURALPERSON.equals(naturalPersonConfirmSubmitDto.getMakerType())){
            if(naturalPersonConfirmSubmitDto.getServiceProviderId() == null){
                return R.fail("请输入服务商id");
            }
            if(StringUtils.isBlank(naturalPersonConfirmSubmitDto.getInvoiceCategory())){
                return R.fail("请输入发票分类");
            }
        }
        if(!ObjectType.ENTERPRISEPEOPLE.equals(objectType) && !!ObjectType.MAKERPEOPLE.equals(objectType)){
            return R.fail("只有商户和创客可以申请自助开票");
        }
        Map<String, List<InvoiceListExcelDTO>> collect = naturalPersonConfirmSubmitDto.getList().stream().collect(Collectors.groupingBy(InvoiceListExcelDTO::getPayer));
        Set<String> strings = collect.keySet();
        for (String s: strings) {
            List<InvoiceListExcelDTO> invoiceListExcelDtos = collect.get(s);
            if(null == invoiceListExcelDtos && invoiceListExcelDtos.size() < 1){
                return R.fail("参数错误！！");
            }
            SelfHelpInvoiceEntity selfHelpInvoiceEntity = new SelfHelpInvoiceEntity();
            EnterpriseEntity enterpriseEntity = iUserClient.queryEnterpriseByName(invoiceListExcelDtos.get(0).getPayer());
            if(null == enterpriseEntity){
                return R.fail("购买方，必须是平台商户会员");
            }
            selfHelpInvoiceEntity.setEnterpriseId(enterpriseEntity.getId());
            if(ObjectType.ENTERPRISEPEOPLE.equals(objectType)){
                selfHelpInvoiceEntity.setApplyEnterpriseId(objectId);
            }
            if(ObjectType.MAKERPEOPLE.equals(objectType)){
                selfHelpInvoiceEntity.setApplyMakerId(objectId);
            }

            if(MakerType.NATURALPERSON.equals(naturalPersonConfirmSubmitDto.getMakerType())){
                selfHelpInvoiceEntity.setMakerType(MakerType.NATURALPERSON);
            }else if(MakerType.INDIVIDUALENTERPRISE.equals(naturalPersonConfirmSubmitDto.getMakerType())){

                selfHelpInvoiceEntity.setMakerType(MakerType.INDIVIDUALENTERPRISE);
            }else{
                selfHelpInvoiceEntity.setMakerType(MakerType.INDIVIDUALBUSINESS);
            }

            selfHelpInvoiceEntity.setListFile(naturalPersonConfirmSubmitDto.getListFile());
            selfHelpInvoiceEntity.setPayType(naturalPersonConfirmSubmitDto.getPayType());
            selfHelpInvoiceEntity.setApplyNum(1);
            selfHelpInvoiceEntity.setAddressId(naturalPersonConfirmSubmitDto.getAddressId());
            save(selfHelpInvoiceEntity);
            //个体户和个独
            if(MakerType.INDIVIDUALENTERPRISE.equals(naturalPersonConfirmSubmitDto.getMakerType()) || MakerType.INDIVIDUALBUSINESS.equals(naturalPersonConfirmSubmitDto.getMakerType())){
                SelfHelpInvoiceSpEntity selfHelpInvoiceSpEntity = new SelfHelpInvoiceSpEntity();
                selfHelpInvoiceSpEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
                selfHelpInvoiceSpEntity.setServiceProviderId(naturalPersonConfirmSubmitDto.getServiceProviderId());
                selfHelpInvoiceSpEntity.setApplyState(SelfHelpInvoiceSpApplyState.ALLOCATED);
                selfHelpInvoiceSpEntity.setApplyDesc("");
                selfHelpInvoiceSpEntity.setAuditDesc("");
                selfHelpInvoiceSpEntity.setAddressId(naturalPersonConfirmSubmitDto.getAddressId());
                selfHelpInvoiceSpEntity.setAddressType(AddressType.TOCUSTOMER);
                selfHelpInvoiceSpService.save(selfHelpInvoiceSpEntity);
            }

            for(InvoiceListExcelDTO invoiceListExcelDto : invoiceListExcelDtos){
                MakerEntity makerEntity = iUserClient.queryMakerByPhoneNumber(invoiceListExcelDto.getPhoneNumber());
                SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = new SelfHelpInvoiceDetailEntity();
                selfHelpInvoiceDetailEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
                selfHelpInvoiceDetailEntity.setInvoicePeopleName(invoiceListExcelDto.getInvoicePeopleName());
                if(MakerType.NATURALPERSON.equals(naturalPersonConfirmSubmitDto.getMakerType())){
                    selfHelpInvoiceDetailEntity.setMakerType(MakerType.NATURALPERSON);
                    SelfHelpInvoicePersonEntity selfHelpInvoicePersonEntity = selfHelpInvoicePersonService.findCardNo(invoiceListExcelDto.getIdcardNo());
                    if(null != makerEntity){
                        selfHelpInvoiceDetailEntity.setMakerId(makerEntity.getId());
                    }else if(null != selfHelpInvoicePersonEntity){
                        selfHelpInvoiceDetailEntity.setNoneMakerInvoicePersonId(selfHelpInvoicePersonEntity.getId());
                    }else{
                        SelfHelpInvoicePersonEntity personEntity = new SelfHelpInvoicePersonEntity();
                        personEntity.setIdcardNo(invoiceListExcelDto.getIdcardNo());
                        personEntity.setIdcardName(invoiceListExcelDto.getInvoicePeopleName());
                        personEntity.setIdcardPic(invoiceListExcelDto.getPositiveIdCard());
                        personEntity.setIdcardPicBack(invoiceListExcelDto.getBackIdCard());
                        personEntity.setPhoneNumber(invoiceListExcelDto.getPhoneNumber());
                        selfHelpInvoicePersonService.save(personEntity);
                        selfHelpInvoiceDetailEntity.setNoneMakerInvoicePersonId(personEntity.getId());
                    }
                }else if(MakerType.INDIVIDUALENTERPRISE.equals(naturalPersonConfirmSubmitDto.getMakerType())){

                    IndividualBusinessEntity individualBusinessEntity = iUserClient.queryIndividualBusinessByMakerIdAndIbtaxNo(makerEntity.getId(), invoiceListExcelDto.getAloneSocialCreditCode());
                    selfHelpInvoiceDetailEntity.setMakerType(MakerType.INDIVIDUALENTERPRISE);
                    selfHelpInvoiceDetailEntity.setIndividualId(individualBusinessEntity.getId());

                }else{
                    IndividualEnterpriseEntity individualEnterpriseEntity = iUserClient.queryIndividualEnterpriseByMakerIdAndIbtaxNo(makerEntity.getId(), invoiceListExcelDto.getSocialCreditCode());
                    selfHelpInvoiceDetailEntity.setMakerType(MakerType.INDIVIDUALBUSINESS);
                    selfHelpInvoiceDetailEntity.setIndividualId(individualEnterpriseEntity.getId());

                }
                selfHelpInvoiceDetailEntity.setProjectName(invoiceListExcelDto.getProjectName());
                selfHelpInvoiceDetailEntity.setInvoiceType(invoiceListExcelDto.getInvoiceCategory());
                selfHelpInvoiceDetailEntity.setValueAddedTaxRate(new BigDecimal(invoiceListExcelDto.getTaxRate()));
                selfHelpInvoiceDetailEntity.setChargeMoneyNum(new BigDecimal(invoiceListExcelDto.getTaxTotalprice()));
                selfHelpInvoiceDetailEntity.setFlowContractUrl(invoiceListExcelDto.getPaymentReceipt());
                selfHelpInvoiceDetailEntity.setBusinessContractUrl(invoiceListExcelDto.getBusinessContract());
                if(null != makerEntity){
                   //加业务合同
                   AgreementEntity agreementEntity = iUserClient.queryEntMakSupplementaryAgreement(makerEntity.getId(), enterpriseEntity.getId());
                   if(null == agreementEntity){
                       iUserClient.createMakerToEnterpriseSupplement(enterpriseEntity.getId(),makerEntity.getId(),invoiceListExcelDto.getBusinessContract());
                   }
                }
                selfHelpInvoiceDetailEntity.setServiceInvoiceFee(new BigDecimal("0"));
                selfHelpInvoiceDetailEntity.setIdendityConfirmFee(new BigDecimal("0"));
                selfHelpInvoiceDetailEntity.setPayProviderFee(new BigDecimal("0"));
                selfHelpInvoiceDetailEntity.setInvoiceType(naturalPersonConfirmSubmitDto.getInvoiceType());
                selfHelpInvoiceDetailService.save(selfHelpInvoiceDetailEntity);

                AcceptPaysheetCsEntity acceptPaysheetCsEntity = new AcceptPaysheetCsEntity();
                acceptPaysheetCsEntity.setSelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
                acceptPaysheetCsEntity.setAcceptPaysheetCsType(AcceptPaysheetType.SINGLE);
                acceptPaysheetCsEntity.setSelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
                acceptPaysheetCsEntity.setServiceTimeStart(new Date());
                acceptPaysheetCsEntity.setServiceTimeEnd(new Date());
                acceptPaysheetCsEntity.setAcceptPaysheetCsUrl(invoiceListExcelDto.getPaymentAcceptance());
                acceptPaysheetCsService.save(acceptPaysheetCsEntity);
                if(MakerType.INDIVIDUALENTERPRISE.equals(naturalPersonConfirmSubmitDto.getMakerType()) || MakerType.INDIVIDUALBUSINESS.equals(naturalPersonConfirmSubmitDto.getMakerType())){
                    SelfHelpInvoiceSpDetailEntity selfHelpInvoiceSpDetailEntity = new SelfHelpInvoiceSpDetailEntity();
                    selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
                    selfHelpInvoiceSpDetailService.save(selfHelpInvoiceSpDetailEntity);
                }
            }
        }
        return R.success("提交成功");
    }

    @Override
    public R querySelfInvoiceList(ObjectType objectType, Long objectId, MakerType makerType,String startTiem,String endTime,IPage<SelfInvoiceListVo> page) {
        if(ObjectType.MAKERPEOPLE.equals(objectType)){
            return R.data(page.setRecords(baseMapper.queryMakerSelfInvoiceList(objectId,makerType,startTiem,endTime,page)));
        }else{
            return R.data(page.setRecords(baseMapper.querySelfInvoiceList(objectId,makerType,startTiem,endTime,page)));
        }

    }

    @Override
    public R queryServiceProviderSelfInvoiceList(Long serviceProviderId, MakerType makerType, String startTiem, String endTime, IPage<SelfInvoiceListVo> page) {
        return R.data(page.setRecords(baseMapper.queryServiceProviderSelfInvoiceList(serviceProviderId,makerType,startTiem,endTime,page)));
    }


    @Override
    public R querySelfInvoiceDetails(Long selfHelpInvoiceId) {
        Map map = new HashMap();
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(selfHelpInvoiceId);
        if(null == selfHelpInvoiceEntity){
            return R.fail("请输入正确的自助开票id");
        }
        EnterpriseEntity enterpriseEntity = iUserClient.queryEnterpriseById(selfHelpInvoiceEntity.getApplyEnterpriseId());
        if(null == enterpriseEntity){
            return R.fail("数据错误！");
        }
        map.put("enterpriseName",enterpriseEntity.getEnterpriseName());
        map.put("payType",selfHelpInvoiceEntity.getPayType());
        map.put("applyState",selfHelpInvoiceEntity.getCurrentState());
        AddressEntity addressEntity = addressService.getById(selfHelpInvoiceEntity.getAddressId());
        map.put("addressId",selfHelpInvoiceEntity.getAddressId());
        map.put("addressName",null != addressEntity && null != addressEntity.getAddressName() ? addressEntity.getAddressName() : null);
        map.put("addressPhone",null != addressEntity && null != addressEntity.getAddressPhone() ? addressEntity.getAddressPhone() : null);
        map.put("province",null != addressEntity && null != addressEntity.getProvince() ? addressEntity.getProvince() : null);
        map.put("city",null != addressEntity && null != addressEntity.getCity() ? addressEntity.getCity() : null);
        map.put("area",null != addressEntity && null != addressEntity.getArea() ? addressEntity.getArea() : null);
        map.put("detailedAddress",null != addressEntity && null != addressEntity.getDetailedAddress() ? addressEntity.getDetailedAddress() : null);
        List<SelfInvoiceDetailVo> selfInvoiceDetailVo = baseMapper.querySelfInvoiceDetail(selfHelpInvoiceEntity.getId());
        map.put("selfInvoiceDetailVos",selfInvoiceDetailVo);
        //已开票结束
        if(SelfHelpInvoiceApplyState.INVOICED.equals(selfHelpInvoiceEntity.getCurrentState()) || SelfHelpInvoiceApplyState.TOPAY.equals(selfHelpInvoiceEntity.getCurrentState()) || SelfHelpInvoiceApplyState.PAID.equals(selfHelpInvoiceEntity.getCurrentState())){
            map.put("totalPayProviderFee",selfHelpInvoiceEntity.getTotalPayProviderFee());
            map.put("serviceAndTaxMoney",selfHelpInvoiceEntity.getServiceAndTaxMoney());
            map.put("serviceFee",selfHelpInvoiceEntity.getServiceFee());
            map.put("serviceRate",selfHelpInvoiceEntity.getServiceRate());
            map.put("serviceTax",selfHelpInvoiceEntity.getServiceTax());
            map.put("serviceInvoiceFee",selfHelpInvoiceEntity.getServiceInvoiceFee());
            map.put("idendityConfirmFee",selfHelpInvoiceEntity.getIdendityConfirmFee());
            SelfHelpInvoiceFeeEntity selfHelpInvoiceFeeEntity = selfHelpInvoiceFeeService.findBySelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
            SelfHelpInvoiceAccountEntity selfHelpInvoiceAccountEntity = null;
            if(null != selfHelpInvoiceFeeEntity){
                 selfHelpInvoiceAccountEntity = selfHelpInvoiceAccountService.getById(selfHelpInvoiceFeeEntity.getHandPayAccountId());
            }
            map.put("selfHelpInvoiceFeeId",selfHelpInvoiceFeeEntity == null ? selfHelpInvoiceFeeEntity.getId() : "");
            map.put("accountBank",selfHelpInvoiceAccountEntity == null ? "": selfHelpInvoiceAccountEntity.getAccountBank());
            map.put("accountName",selfHelpInvoiceAccountEntity == null ? "": selfHelpInvoiceAccountEntity.getAccountName());
            map.put("basicAccountBank",selfHelpInvoiceAccountEntity == null ? "" : selfHelpInvoiceAccountEntity.getBasicAccountBank());
            map.put("accountNo",selfHelpInvoiceAccountEntity == null ? "" : selfHelpInvoiceAccountEntity.getAccountNo());
            map.put("payCertificate",selfHelpInvoiceFeeEntity == null ? "" : selfHelpInvoiceFeeEntity.getPayCertificate());

            if(SelfHelpInvoiceApplyState.INVOICED.equals(selfHelpInvoiceEntity.getCurrentState())){
                SelfHelpInvoiceSpEntity selfHelpInvoiceSpEntity = selfHelpInvoiceSpService.findBySelfHelpInvoiceId(selfHelpInvoiceEntity.getId());
                SelfHelpInvoiceExpressEntity bySelfHelpInvoiceApplyProviderId = selfHelpInvoiceExpressService.findBySelfHelpInvoiceApplyProviderId(selfHelpInvoiceSpEntity.getId());
                String expressCompanyName = bySelfHelpInvoiceApplyProviderId.getExpressCompanyName();
                String expressNo = bySelfHelpInvoiceApplyProviderId.getExpressNo();
                KdniaoTrackQueryUtil kdniaoTrackQueryUtil = new KdniaoTrackQueryUtil();
                //查询快递
                String orderTracesByJson = "";
                try {
                    orderTracesByJson = kdniaoTrackQueryUtil.getOrderTracesByJson(expressCompanyName, expressNo);
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
            }
        }
        return R.data(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R submitSelfHelpInvoice(Long selfHelpInvoiceId) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(selfHelpInvoiceId);
        if(null == selfHelpInvoiceEntity){
            return R.fail("请输入正确的开票id");
        }
        selfHelpInvoiceEntity.setCurrentState(SelfHelpInvoiceApplyState.AUDITING);
        saveOrUpdate(selfHelpInvoiceEntity);
        List<SelfHelpInvoiceDetailEntity> selfHelpInvoiceDetailEntities = selfHelpInvoiceDetailService.querySelfHelpInvoiceId(selfHelpInvoiceId);
        for (SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity : selfHelpInvoiceDetailEntities) {
            selfHelpInvoiceDetailEntity.setInvoicePrintState(SelfHelpInvoiceApplyState.AUDITING);
            selfHelpInvoiceDetailService.saveOrUpdate(selfHelpInvoiceDetailEntity);
        }
        SelfHelpInvoiceApplyEntity selfHelpInvoiceApplyEntity = new SelfHelpInvoiceApplyEntity();
        selfHelpInvoiceApplyEntity.setSelfHelpInvoiceId(selfHelpInvoiceId);
        selfHelpInvoiceApplyEntity.setApplyState(SelfHelpInvoiceApplyState.AUDITING);
        selfHelpInvoiceApplyEntity.setApplyDesc("");
        selfHelpInvoiceApplyService.save(selfHelpInvoiceApplyEntity);
        return R.success("提交成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R confirmModification(Long selfHelpInvoiceId, List<ModificationDTO> list) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(selfHelpInvoiceId);
        if(null == selfHelpInvoiceEntity){
            return R.fail("请输入正确的开票id");
        }
        for (ModificationDTO modificationDto : list) {
            SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = selfHelpInvoiceDetailService.getById(modificationDto.getSelfHelpInvoiceDetailId());
            Long makerId = selfHelpInvoiceDetailEntity.getMakerId();
            if(MakerType.NATURALPERSON.equals(selfHelpInvoiceEntity.getMakerType())){
                if(null != makerId){
                    selfHelpInvoiceDetailEntity.setFlowContractUrl(modificationDto.getPaymentReceipt());
                    selfHelpInvoiceDetailEntity.setBusinessContractUrl(modificationDto.getBusinessContract());
                    selfHelpInvoiceDetailService.saveOrUpdate(selfHelpInvoiceDetailEntity);
                }
                Long noneMakerInvoicePersonId = selfHelpInvoiceDetailEntity.getNoneMakerInvoicePersonId();
                if(null != noneMakerInvoicePersonId){
                    selfHelpInvoiceDetailEntity.setFlowContractUrl(modificationDto.getPaymentReceipt());
                    selfHelpInvoiceDetailEntity.setBusinessContractUrl(modificationDto.getBusinessContract());
                    selfHelpInvoiceDetailService.saveOrUpdate(selfHelpInvoiceDetailEntity);
                    SelfHelpInvoicePersonEntity selfHelpInvoicePersonEntity = selfHelpInvoicePersonService.getById(noneMakerInvoicePersonId);
                    selfHelpInvoicePersonEntity.setIdcardPicBack(modificationDto.getBackIdCard());
                    selfHelpInvoicePersonEntity.setIdcardPic(modificationDto.getPositiveIdCard());
                    selfHelpInvoicePersonService.saveOrUpdate(selfHelpInvoicePersonEntity);
                }
            }else if(MakerType.INDIVIDUALENTERPRISE.equals(selfHelpInvoiceEntity.getMakerType())){
                selfHelpInvoiceDetailEntity.setBusinessContractUrl(modificationDto.getBusinessContract());
                selfHelpInvoiceDetailEntity.setFlowContractUrl(modificationDto.getPaymentReceipt());
                selfHelpInvoiceDetailService.saveOrUpdate(selfHelpInvoiceDetailEntity);
            }else{
                selfHelpInvoiceDetailEntity.setBusinessContractUrl(modificationDto.getBusinessContract());
                selfHelpInvoiceDetailEntity.setFlowContractUrl(modificationDto.getPaymentReceipt());
                selfHelpInvoiceDetailService.saveOrUpdate(selfHelpInvoiceDetailEntity);
            }

        }
        return R.success("修改成功");
    }

    @Override
    public R confirmPayment(Long selfHelpInvoiceId,Long selfHelpInvoiceFeeId, String payCertificate) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(selfHelpInvoiceId);
        if(null == selfHelpInvoiceEntity){
            return R.fail("请输入正确的开票id");
        }
        SelfHelpInvoiceFeeEntity selfHelpInvoiceFeeEntity = selfHelpInvoiceFeeService.getById(selfHelpInvoiceFeeId);
        selfHelpInvoiceFeeEntity.setPayCertificate(payCertificate);
        selfHelpInvoiceFeeService.saveOrUpdate(selfHelpInvoiceFeeEntity);
        selfHelpInvoiceEntity.setCurrentState(SelfHelpInvoiceApplyState.PAID);
        saveOrUpdate(selfHelpInvoiceEntity);
        SelfHelpInvoiceApplyEntity bySelfHelpInvoiceId = selfHelpInvoiceApplyService.getBySelfHelpInvoiceId(selfHelpInvoiceId);
        bySelfHelpInvoiceId.setApplyState(SelfHelpInvoiceApplyState.PAID);
        selfHelpInvoiceApplyService.saveOrUpdate(bySelfHelpInvoiceId);
        List<SelfHelpInvoiceDetailVO> selfHelpInvoiceId1 = selfHelpInvoiceDetailService.getSelfHelpInvoiceId(selfHelpInvoiceId);
        for (SelfHelpInvoiceDetailVO selfHelpInvoiceDetailVO : selfHelpInvoiceId1){
            SelfHelpInvoiceDetailEntity byId = selfHelpInvoiceDetailService.getById(selfHelpInvoiceDetailVO.getId());
            byId.setInvoicePrintState(SelfHelpInvoiceApplyState.PAID);
            selfHelpInvoiceDetailService.saveOrUpdate(byId);
        }
        return R.success("确认支付成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createCrowdsourcingInvoice(CreateCrowdsourcingInvoiceDTO createCrowdsourcingInvoiceDTO) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(createCrowdsourcingInvoiceDTO.getSelfHelpInvoiceId());
        if(null == selfHelpInvoiceEntity){
            return R.fail("请输入自助开票id");
        }
        List<SelfHelpInvoiceDetailVO> selfHelpInvoiceDetailVOS = selfHelpInvoiceDetailService.getSelfHelpInvoiceId(createCrowdsourcingInvoiceDTO.getSelfHelpInvoiceId());
        if(null == selfHelpInvoiceDetailVOS || selfHelpInvoiceDetailVOS.size() <= 0 || selfHelpInvoiceDetailVOS.size() != createCrowdsourcingInvoiceDTO.getList().size()){
            return R.fail("请核对自助开票id");
        }
        selfHelpInvoiceEntity.setCurrentState(SelfHelpInvoiceApplyState.PAID);

        List<CrowdsourcingInvoiceDTO> list = createCrowdsourcingInvoiceDTO.getList();
        for (CrowdsourcingInvoiceDTO crowdsourcingInvoiceDTO : list){
            SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = selfHelpInvoiceDetailService.getById(crowdsourcingInvoiceDTO.getSelfHelpInvoiceDetailId());
            selfHelpInvoiceDetailEntity.setInvoicePrintState(SelfHelpInvoiceApplyState.INVOICED);
            SelfHelpInvoiceSpDetailEntity selfHelpInvoiceSpDetailEntity = selfHelpInvoiceSpDetailService.findBySelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
            selfHelpInvoiceSpDetailEntity.setTaxScanPictures(crowdsourcingInvoiceDTO.getTaxScanPictures());
            selfHelpInvoiceSpDetailEntity.setInvoiceScanPictures(crowdsourcingInvoiceDTO.getInvoiceScanPictures());
            selfHelpInvoiceDetailService.saveOrUpdate(selfHelpInvoiceDetailEntity);
        }

        SelfHelpInvoiceExpressEntity selfHelpInvoiceExpressEntity = new SelfHelpInvoiceExpressEntity();
        selfHelpInvoiceExpressEntity.setExpressCompanyName(createCrowdsourcingInvoiceDTO.getExpressCompanyName());
        selfHelpInvoiceExpressEntity.setExpressNo(createCrowdsourcingInvoiceDTO.getExpressNo());
        selfHelpInvoiceExpressEntity.setSelfHelpInvoiceApplyProviderId(selfHelpInvoiceEntity.getOriginalSelfHelpId());
        selfHelpInvoiceExpressService.saveOrUpdate(selfHelpInvoiceExpressEntity);

        SelfHelpInvoiceApplyEntity bySelfHelpInvoiceId = selfHelpInvoiceApplyService.getBySelfHelpInvoiceId(createCrowdsourcingInvoiceDTO.getSelfHelpInvoiceId());
        bySelfHelpInvoiceId.setApplyState(SelfHelpInvoiceApplyState.PAID);
        selfHelpInvoiceApplyService.saveOrUpdate(bySelfHelpInvoiceId);
        saveOrUpdate(selfHelpInvoiceEntity);

        return R.success("开票成功");
    }

}
