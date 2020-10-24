package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.CollectionUtil;
import com.lgyun.common.tool.KdniaoTrackQueryUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.SelfHelpInvoiceDetailInvoiceTaxDTO;
import com.lgyun.system.order.dto.SelfHelpInvoiceDetailsByServiceProviderDTO;
import com.lgyun.system.order.dto.SelfHelpInvoiceExpressDTO;
import com.lgyun.system.order.dto.SelfHelpInvoicesByEnterpriseDTO;
import com.lgyun.system.order.dto.admin.ToExamineSelfHelpInvoiceDTO;
import com.lgyun.system.order.entity.*;
import com.lgyun.system.order.mapper.SelfHelpInvoiceMapper;
import com.lgyun.system.order.service.*;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.order.vo.admin.SelfHelpInvoiceAdminDetailVO;
import com.lgyun.system.order.vo.admin.SelfHelpInvoiceAdminVO;
import com.lgyun.system.order.vo.admin.SelfHelpInvoiceDetailAdminVO;
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

import javax.annotation.Resource;
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

    @Resource
    private IUserClient iUserClient;
    @Resource
    private ISelfHelpInvoiceSpService selfHelpInvoiceSpService;
    @Resource
    private ISelfHelpInvoiceSpDetailService selfHelpInvoiceSpDetailService;
    @Resource
    private ISelfHelpInvoiceExpressService selfHelpInvoiceExpressService;

    @Resource
    private ISelfHelpInvoiceAccountService selfHelpInvoiceAccountService;

    @Resource
    private ISelfHelpInvoiceFeeService selfHelpInvoiceFeeService;

    @Autowired
    @Lazy
    private ISelfHelpInvoiceDetailService selfHelpInvoiceDetailService;

    @Override
    public R<IPage<SelfHelpInvoiceListByEnterpriseVO>> getSelfHelfInvoicesByEnterprise(Long enterpriseId, InvoicePeopleType invoicePeopleType, SelfHelpInvoicesByEnterpriseDTO selfHelpInvoicesByEnterpriseDto, IPage<SelfHelpInvoiceListByEnterpriseVO> page) {

        if (selfHelpInvoicesByEnterpriseDto.getBeginDate() != null && selfHelpInvoicesByEnterpriseDto.getEndDate() != null) {
            if (selfHelpInvoicesByEnterpriseDto.getBeginDate().after(selfHelpInvoicesByEnterpriseDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getSelfHelfInvoicesByEnterprise(enterpriseId, invoicePeopleType, selfHelpInvoicesByEnterpriseDto, page)));
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
                } catch (Exception e) {
                    log.error("查询快递异常", e);
                }
            }

            selfHelpInvoiceExpressByEnterpriseVO.setExpressMessage(expressMessage);
        }

        return R.data(page.setRecords(selfHelpInvoiceExpressByEnterpriseVOList));
    }

    @Override
    public R<IPage<SelfHelpInvoiceListByServiceProviderVO>> getSelfHelfInvoicesByServiceProvider(Long serviceProviderId, InvoicePeopleType invoicePeopleType, SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState, SelfHelpInvoiceDetailsByServiceProviderDTO selfHelpInvoiceDetailsByServiceProviderDto, IPage<SelfHelpInvoiceListByServiceProviderVO> page) {

        if (selfHelpInvoiceDetailsByServiceProviderDto.getBeginDate() != null && selfHelpInvoiceDetailsByServiceProviderDto.getEndDate() != null) {
            if (selfHelpInvoiceDetailsByServiceProviderDto.getBeginDate().after(selfHelpInvoiceDetailsByServiceProviderDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getSelfHelfInvoicesByServiceProvider(serviceProviderId, invoicePeopleType, selfHelpInvoiceSpApplyState, selfHelpInvoiceDetailsByServiceProviderDto, page)));
    }

    @Override
    public R<SelfHelpInvoiceSingleByServiceProviderVO> getSingleSelfHelfInvoiceByServiceProvider(Long serviceProviderId, Long selfHelpInvoiceDetailId) {
        return R.data(baseMapper.getSingleSelfHelfInvoiceByServiceProvider(serviceProviderId, selfHelpInvoiceDetailId));
    }

    @Override
    public R<String> uploadInvoiceTaxByProvider(ServiceProviderWorkerEntity serviceProviderWorkerEntity, SelfHelpInvoiceDetailInvoiceTaxDTO selfHelpInvoiceDetailInvoiceTaxDto) {

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
        SelfHelpInvoiceSpEntity selfHelpInvoiceSpEntity = selfHelpInvoiceSpService.findByServiceProviderIdAndSelfHelpInvoiceId(serviceProviderWorkerEntity.getServiceProviderId(), selfHelpInvoiceDetailEntity.getSelfHelpInvoiceId());
        if (selfHelpInvoiceSpEntity == null) {
            return R.fail("自助开票明细不属于当前服务商");
        }

        if (InvoicePeopleType.NATURALPERSON.equals(selfHelpInvoiceDetailEntity.getInvoicePeopleType()) && StringUtils.isBlank(selfHelpInvoiceDetailInvoiceTaxDto.getTaxScanPictures())) {
            return R.fail("自然人类型发票不能缺少税票");
        }

        SelfHelpInvoiceSpDetailEntity selfHelpInvoiceSpDetailEntity = selfHelpInvoiceSpDetailService.findBySelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
        if (selfHelpInvoiceSpDetailEntity == null) {
            selfHelpInvoiceSpDetailEntity = new SelfHelpInvoiceSpDetailEntity();
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceApplyProviderId(selfHelpInvoiceSpEntity.getId());
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
            selfHelpInvoiceSpDetailEntity.setInvoiceScanPictures(selfHelpInvoiceDetailInvoiceTaxDto.getInvoiceScanPictures());
            selfHelpInvoiceSpDetailEntity.setTaxScanPictures(selfHelpInvoiceDetailInvoiceTaxDto.getTaxScanPictures());
            selfHelpInvoiceSpDetailEntity.setInvoiceOperatePerson(serviceProviderWorkerEntity.getWorkerName());
            selfHelpInvoiceSpDetailService.save(selfHelpInvoiceSpDetailEntity);
        } else {
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceApplyProviderId(selfHelpInvoiceSpEntity.getId());
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
            selfHelpInvoiceSpDetailEntity.setInvoiceScanPictures(selfHelpInvoiceDetailInvoiceTaxDto.getInvoiceScanPictures());
            selfHelpInvoiceSpDetailEntity.setTaxScanPictures(selfHelpInvoiceDetailInvoiceTaxDto.getTaxScanPictures());
            selfHelpInvoiceSpDetailEntity.setInvoiceOperatePerson(serviceProviderWorkerEntity.getWorkerName());
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
            } catch (Exception e) {
                log.error("查询快递异常", e);
            }
        }

        selfHelpInvoiceExpressByEnterpriseProviderVO.setExpressMessage(expressMessage);

        return R.data(selfHelpInvoiceExpressByEnterpriseProviderVO);
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

            case INDIVIDUALENTERPRISE:
                IndividualEnterpriseEntity individualEnterpriseEntity = iUserClient.queryIndividualEnterpriseById(allKindEnterpriseId);
                if (individualEnterpriseEntity != null) {
                    bizType = individualEnterpriseEntity.getBizType();
                }
                break;

            case INDIVIDUALBUSINESS:
                IndividualBusinessEntity individualBusinessEntity = iUserClient.queryIndividualBusinessById(allKindEnterpriseId);
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
        } catch (Exception e) {
            log.info("快鸟接口访问失败");
        }
        map.put("orderTracesByJson", orderTracesByJson);
        return R.data(map);
    }

    @Override
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
    public R getServiceCrowdSour(Long serviceProviderId, String enterpriseName,String startTime,String endTime,SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState,IPage<SelfHelpInvoiceCrowdSourcingVO> page) {
        return R.data(page.setRecords(baseMapper.getServiceCrowdSour(serviceProviderId,enterpriseName,startTime,endTime,selfHelpInvoiceSpApplyState,page)));
    }

    @Override
    public R getServiceCrowdSourDetails(Long providerSelfHelpInvoiceId) {
        Map map = new HashMap();

        ServiceCrowdSourcingDetailVO serviceCrowdSourDetails = baseMapper.getServiceCrowdSourDetails(providerSelfHelpInvoiceId);
        map.put("serviceCrowdSourDetails",serviceCrowdSourDetails);
        try{
            if(StringUtil.isNotBlank(serviceCrowdSourDetails.getExpressNo()) && StringUtil.isNotBlank(serviceCrowdSourDetails.getExpressCompanyName())){
                KdniaoTrackQueryUtil kdniaoTrackQueryUtil = new KdniaoTrackQueryUtil();
                String orderTracesByJson = kdniaoTrackQueryUtil.getOrderTracesByJson(serviceCrowdSourDetails.getExpressCompanyName(), serviceCrowdSourDetails.getExpressNo());
                map.put("orderTracesByJson",orderTracesByJson);
            }else{
                map.put("orderTracesByJson","");
            }
        }catch (Exception e){
            log.error("快鸟接口出错！！！",e);
            map.put("orderTracesByJson","");
        }
        return R.data(map);
    }

    @Override
    @Transactional
    public R savePortalSignInvoice(String serviceProviderName,Long providerSelfHelpInvoiceId,String expressNo, String expressCompanyName, String invoiceScanPictures, String taxScanPictures) {
        SelfHelpInvoiceSpDetailEntity selfHelpInvoiceSpDetailEntity = selfHelpInvoiceSpDetailService.getById(providerSelfHelpInvoiceId);
        if(null != selfHelpInvoiceSpDetailEntity){
            return R.fail("数据错误");
        }
        selfHelpInvoiceSpDetailEntity.setInvoiceScanPictures(invoiceScanPictures);
        selfHelpInvoiceSpDetailEntity.setTaxScanPictures(taxScanPictures);
        selfHelpInvoiceSpDetailEntity.setInvoiceOperatePerson(serviceProviderName);
        selfHelpInvoiceSpDetailService.saveOrUpdate(selfHelpInvoiceSpDetailEntity);

        SelfHelpInvoiceExpressEntity selfHelpInvoiceExpressEntity = new SelfHelpInvoiceExpressEntity();
        selfHelpInvoiceExpressEntity.setSelfHelpInvoiceApplyProviderId(selfHelpInvoiceSpDetailEntity.getSelfHelpInvoiceApplyProviderId());
        selfHelpInvoiceExpressEntity.setExpressNo(expressNo);
        selfHelpInvoiceExpressEntity.setExpressCompanyName(expressCompanyName);
        selfHelpInvoiceExpressEntity.setOperatePerson(serviceProviderName);
        selfHelpInvoiceExpressEntity.setExpressUpdatePersonTel(serviceProviderName);
        selfHelpInvoiceExpressService.save(selfHelpInvoiceExpressEntity);

        //更新明细里面的数据
        SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = selfHelpInvoiceDetailService.getById(selfHelpInvoiceSpDetailEntity.getSelfHelpInvoiceDetailId());
        selfHelpInvoiceDetailEntity.setInvoicePrintState(InvoicePrintState.INVOICESUCCESS);
        selfHelpInvoiceDetailService.saveOrUpdate(selfHelpInvoiceDetailEntity);
        if(selfHelpInvoiceDetailService.getSelfHelpInvoiceDetails(selfHelpInvoiceDetailEntity.getSelfHelpInvoiceId(),selfHelpInvoiceDetailEntity.getId())){
            //更新主表的发票状态
            SelfHelpInvoiceSpEntity selfHelpInvoiceSpEntity = selfHelpInvoiceSpService.getById(selfHelpInvoiceSpDetailEntity.getSelfHelpInvoiceApplyProviderId());
            selfHelpInvoiceSpEntity.setApplyState(SelfHelpInvoiceSpApplyState.INVOICED);
            selfHelpInvoiceSpService.saveOrUpdate(selfHelpInvoiceSpEntity);

            SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(selfHelpInvoiceDetailEntity.getSelfHelpInvoiceId());
            selfHelpInvoiceEntity.setCurrentState(SelfHelpInvoiceApplyState.INVOICED);
            saveOrUpdate(selfHelpInvoiceEntity);
        }
        return R.success("操作成功");
    }

    @Override
    public R getAdminMakerTypeSelfHelpInvoice(String enterpriseName, String startTime, String endTime,MakerType makerType, IPage<SelfHelpInvoiceAdminVO> page) {
        return R.data(page.setRecords(baseMapper.getAdminMakerTypeSelfHelpInvoice(enterpriseName,startTime,endTime,makerType,page)));
    }

    @Override
    public R getMakerTypeSelfHelpInvoiceDetails(Long selfHelpInvoiceId) {
        Map map = new HashMap();
        SelfHelpInvoiceAdminDetailVO makerTypeSelfHelpInvoiceDetails = baseMapper.getMakerTypeSelfHelpInvoiceDetails(selfHelpInvoiceId);
        List<SelfHelpInvoiceDetailAdminVO> selfHelpInvoiceDetailAdminVOS= selfHelpInvoiceDetailService.getSelfHelpInvoiceIdAll(selfHelpInvoiceId);
        map.put("makerTypeSelfHelpInvoiceDetails",makerTypeSelfHelpInvoiceDetails);
        map.put("selfHelpInvoiceDetailAdminVOS",selfHelpInvoiceDetailAdminVOS);
        String orderTracesByJson = "";
        if(selfHelpInvoiceDetailAdminVOS.size() > 0){
            String expressCompanyName = selfHelpInvoiceDetailAdminVOS.get(0).getExpressCompanyName();
            String expressNo = selfHelpInvoiceDetailAdminVOS.get(0).getExpressNo();
            try {
                KdniaoTrackQueryUtil kdniaoTrackQueryUtil = new KdniaoTrackQueryUtil();
                orderTracesByJson = kdniaoTrackQueryUtil.getOrderTracesByJson(expressCompanyName, expressNo);
            }catch (Exception e){

            }
        }
        map.put("orderTracesByJson",orderTracesByJson);
        return R.data(map);
    }

    @Override
    @Transactional
    public R toExamineSelfHelpInvoice(ToExamineSelfHelpInvoiceDTO toExamineSelfHelpInvoiceDto) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(toExamineSelfHelpInvoiceDto.getSelfHelpInvoiceId());
        if(null == selfHelpInvoiceEntity){
            return R.fail("没有此数据");
        }
        selfHelpInvoiceEntity.setTotlChargeMoneyNum(new BigDecimal(toExamineSelfHelpInvoiceDto.getTotlChargeMoneyNum()));
        selfHelpInvoiceEntity.setServiceRate(new BigDecimal(toExamineSelfHelpInvoiceDto.getServiceRate()));
        selfHelpInvoiceEntity.setServiceTax(new BigDecimal(toExamineSelfHelpInvoiceDto.getServiceTax()));
        selfHelpInvoiceEntity.setServiceAndTaxMoney(new BigDecimal(toExamineSelfHelpInvoiceDto.getServiceandTaxMoney()));
        selfHelpInvoiceEntity.setServiceInvoiceFee(new BigDecimal(toExamineSelfHelpInvoiceDto.getServiceInvoiceFee()));
        selfHelpInvoiceEntity.setTotalPayProviderFee(new BigDecimal(toExamineSelfHelpInvoiceDto.getTotalPayProviderFee()));
        selfHelpInvoiceEntity.setIdendityConfirmFee(new BigDecimal(toExamineSelfHelpInvoiceDto.getIdendityConfirmFee()));
        selfHelpInvoiceEntity.setServiceFee(new BigDecimal(toExamineSelfHelpInvoiceDto.getServiceFee()));
        selfHelpInvoiceEntity.setCurrentState(SelfHelpInvoiceApplyState.TOPAY);
        saveOrUpdate(selfHelpInvoiceEntity);
        List<SelfHelpInvoiceDetailVO> selfHelpInvoiceId = selfHelpInvoiceDetailService.getSelfHelpInvoiceId(toExamineSelfHelpInvoiceDto.getSelfHelpInvoiceId());
        for (SelfHelpInvoiceDetailVO selfHelpInvoiceDetailVO: selfHelpInvoiceId){
            SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = BeanUtil.copy(selfHelpInvoiceDetailVO, SelfHelpInvoiceDetailEntity.class);
            BigDecimal subtract = new BigDecimal(toExamineSelfHelpInvoiceDto.getServiceRate()).subtract(selfHelpInvoiceDetailEntity.getChargeMoneyNum());
            selfHelpInvoiceDetailEntity.setPayProviderFee(subtract);
            selfHelpInvoiceDetailEntity.setInvoicePrintState(InvoicePrintState.APPLYING);
            selfHelpInvoiceDetailService.saveOrUpdate(selfHelpInvoiceDetailEntity);
        }
        SelfHelpInvoiceAccountEntity selfHelpInvoiceAccountEntity = new SelfHelpInvoiceAccountEntity();
        selfHelpInvoiceAccountEntity.setAccountBank(toExamineSelfHelpInvoiceDto.getAccountBank());
        selfHelpInvoiceAccountEntity.setAccountName(toExamineSelfHelpInvoiceDto.getAccountName());
        selfHelpInvoiceAccountEntity.setAccountNo(toExamineSelfHelpInvoiceDto.getAccountNo());
        selfHelpInvoiceAccountEntity.setIsDefault(true);
        selfHelpInvoiceAccountService.save(selfHelpInvoiceAccountEntity);
        SelfHelpInvoiceFeeEntity selfHelpInvoiceFeeEntity = new SelfHelpInvoiceFeeEntity();
        selfHelpInvoiceFeeEntity.setSelfHelpInvoiceId(toExamineSelfHelpInvoiceDto.getSelfHelpInvoiceId());
        selfHelpInvoiceFeeEntity.setPutinDate(new Date());
        selfHelpInvoiceFeeEntity.setGivePriceDate(new Date());
        selfHelpInvoiceFeeEntity.setTotalTaxFee(new BigDecimal(toExamineSelfHelpInvoiceDto.getServiceTax()));
        selfHelpInvoiceFeeEntity.setBasicTaxFee(new BigDecimal(toExamineSelfHelpInvoiceDto.getServiceandTaxMoney()));
        selfHelpInvoiceFeeEntity.setBasicTaxFeeRate(new BigDecimal(toExamineSelfHelpInvoiceDto.getServiceRate()));
        selfHelpInvoiceFeeEntity.setInvoiceFee(new BigDecimal(toExamineSelfHelpInvoiceDto.getServiceInvoiceFee()));
        selfHelpInvoiceFeeEntity.setIdentifyFee(new BigDecimal(toExamineSelfHelpInvoiceDto.getIdendityConfirmFee()));
        selfHelpInvoiceFeeEntity.setHandPayAccountId(selfHelpInvoiceAccountEntity.getId());
        selfHelpInvoiceFeeService.save(selfHelpInvoiceFeeEntity);
        return R.success("审核成功");
    }

    @Override
    public R matchServiceProvider(Long selfHelpInvoiceId,Long selfHelpInvoiceFeeId, Long serviceProviderId, String payCertificate) {
        SelfHelpInvoiceEntity selfHelpInvoiceEntity = getById(serviceProviderId);
        if(null == selfHelpInvoiceEntity){
            return R.fail("没有此数据");
        }
        if(selfHelpInvoiceEntity.getCurrentState().equals("TOPAY")){
            return R.fail("自助开票状态错误");
        }
        SelfHelpInvoiceFeeEntity selfHelpInvoiceFeeEntity = selfHelpInvoiceFeeService.getById(selfHelpInvoiceFeeId);
        selfHelpInvoiceFeeEntity.setPayCertificate(payCertificate);
        selfHelpInvoiceFeeService.saveOrUpdate(selfHelpInvoiceFeeEntity);
        SelfHelpInvoiceSpEntity selfHelpInvoiceSpEntity = new SelfHelpInvoiceSpEntity();
        selfHelpInvoiceSpEntity.setSelfHelpInvoiceId(selfHelpInvoiceId);
        selfHelpInvoiceSpEntity.setServiceProviderId(serviceProviderId);
        selfHelpInvoiceSpEntity.setOperatePerson("平台");
        selfHelpInvoiceSpEntity.setApplyState(SelfHelpInvoiceSpApplyState.ALLOCATED);
        selfHelpInvoiceSpEntity.setApplyDesc("");
        selfHelpInvoiceSpEntity.setAuditDesc("");
        selfHelpInvoiceSpEntity.setChargeMoneyNum(selfHelpInvoiceEntity.getTotlChargeMoneyNum());
        selfHelpInvoiceSpEntity.setValueMoneyNum(selfHelpInvoiceEntity.getTotlChargeMoneyNum());
        selfHelpInvoiceSpEntity.setServiceRate(selfHelpInvoiceEntity.getServiceRate());
        selfHelpInvoiceSpEntity.setServiceAndTaxMoney(selfHelpInvoiceEntity.getServiceAndTaxMoney());
        selfHelpInvoiceSpEntity.setServiceInvoiceFee(selfHelpInvoiceEntity.getServiceInvoiceFee());
        selfHelpInvoiceSpEntity.setIdendityConfirmFee(selfHelpInvoiceFeeEntity.getIdentifyFee());
        selfHelpInvoiceSpEntity.setPayTotalNum(selfHelpInvoiceEntity.getTotlChargeMoneyNum());
        selfHelpInvoiceSpEntity.setAddressId(null);
        selfHelpInvoiceSpEntity.setAddressType(AddressType.TOCUSTOMER);
        selfHelpInvoiceSpService.save(selfHelpInvoiceSpEntity);
        List<SelfHelpInvoiceDetailVO> selfHelpInvoiceIds = selfHelpInvoiceDetailService.getSelfHelpInvoiceId(selfHelpInvoiceId);
        for (SelfHelpInvoiceDetailVO selfHelpInvoiceDetailVO: selfHelpInvoiceIds){
            SelfHelpInvoiceDetailEntity selfHelpInvoiceDetailEntity = BeanUtil.copy(selfHelpInvoiceDetailVO, SelfHelpInvoiceDetailEntity.class);
            SelfHelpInvoiceSpDetailEntity selfHelpInvoiceSpDetailEntity = new SelfHelpInvoiceSpDetailEntity();
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceApplyProviderId(selfHelpInvoiceSpEntity.getId());
            selfHelpInvoiceSpDetailEntity.setSelfHelpInvoiceDetailId(selfHelpInvoiceDetailEntity.getId());
            selfHelpInvoiceSpDetailEntity.setMatchDatetime(new Date());
            selfHelpInvoiceSpDetailService.save(selfHelpInvoiceSpDetailEntity);
        }
        return R.success("操作成功");
    }

    @Override
    public R uploadAdminExpress(Long selfHelpInvoiceId, Long serviceProviderId,String expressNo, String expressCompanyName) {
        SelfHelpInvoiceSpEntity selfHelpInvoiceSpEntity = selfHelpInvoiceSpService.findByServiceProviderIdAndSelfHelpInvoiceId(selfHelpInvoiceId, serviceProviderId);
        if (null == selfHelpInvoiceSpEntity || !selfHelpInvoiceSpEntity.getApplyState().equals("INVOICED")) {
            return R.data("数据错误！");
        }
        SelfHelpInvoiceExpressEntity selfHelpInvoiceExpressEntity = new SelfHelpInvoiceExpressEntity();
        selfHelpInvoiceExpressEntity.setSelfHelpInvoiceApplyProviderId(selfHelpInvoiceSpEntity.getId());
        selfHelpInvoiceExpressEntity.setExpressNo(expressNo);
        selfHelpInvoiceExpressEntity.setExpressCompanyName(expressCompanyName);
        selfHelpInvoiceExpressEntity.setExpressFileUrl("");
        selfHelpInvoiceExpressEntity.setOperatePerson("平台");
        selfHelpInvoiceExpressService.save(selfHelpInvoiceExpressEntity);
        return R.success("成功");
    }
    @Override
    public R uploadAdminInvoice(Long selfHelpInvoiceApplyProviderDetailId, String invoiceScanPictures, String taxScanPictures) {
        SelfHelpInvoiceSpDetailEntity selfHelpInvoiceSpDetailEntity = selfHelpInvoiceSpDetailService.getById(selfHelpInvoiceApplyProviderDetailId);
        selfHelpInvoiceSpDetailEntity.setTaxScanPictures(taxScanPictures);
        selfHelpInvoiceSpDetailEntity.setInvoiceScanPictures(invoiceScanPictures);
        selfHelpInvoiceSpDetailEntity.setMatchDatetime(new Date());
        selfHelpInvoiceSpDetailService.saveOrUpdate(selfHelpInvoiceSpDetailEntity);
        return R.success("上传成功");
    }


}
