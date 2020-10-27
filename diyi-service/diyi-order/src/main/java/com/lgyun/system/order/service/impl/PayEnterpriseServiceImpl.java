package com.lgyun.system.order.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.KdniaoTrackQueryUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.PayEnterpriseDTO;
import com.lgyun.system.order.dto.PayEnterpriseUploadDTO;
import com.lgyun.system.order.entity.*;
import com.lgyun.system.order.excel.PayEnterpriseExcel;
import com.lgyun.system.order.excel.PayEnterpriseImportListener;
import com.lgyun.system.order.mapper.PayEnterpriseMapper;
import com.lgyun.system.order.service.*;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.order.vo.TransactionByBureauServiceProviderInfoVO;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.EnterpriseServiceProviderEntity;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.feign.IUserClient;
import com.lgyun.system.user.vo.TransactionVO;
import com.lgyun.system.user.vo.AdminAgentMainServiceProviderListVO;
import com.lgyun.system.user.vo.AgentMainTransactionVO;
import com.lgyun.system.user.vo.PartnerServiceProviderListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayEnterpriseServiceImpl extends BaseServiceImpl<PayEnterpriseMapper, PayEnterpriseEntity> implements IPayEnterpriseService {

    private IPayEnterpriseReceiptService payEnterpriseReceiptService;
    private IWorksheetService worksheetService;
    private IUserClient userClient;
    private IInvoiceApplicationService invoiceApplicationService;
    private IWorksheetMakerService worksheetMakerService;
    private IPayMakerService payMakerService;
    private IPlatformInvoiceService platformInvoiceService;
    private IPlatformInvoicePayListService platformInvoicePayListService;
    private IMakerTotalInvoiceService makerTotalInvoiceService;
    private IMakerInvoiceService makerInvoiceService;
    private IMakerTaxRecordService makerTaxRecordService;
    private IPlatformInvoiceListService platformInvoiceListService;
    private IInvoiceApplicationPayListService invoiceApplicationPayListService;

    @Override
    public R<IPage<InvoiceEnterpriseVO>> getEnterpriseAll(Long makerId, IPage<InvoiceEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.getEnterpriseAll(makerId, page)));
    }

    @Override
    public R<IPage<InvoiceEnterpriseVO>> getEnterpriseMakerIdAll(Long makerId, Long enterpriseId, IPage<InvoiceEnterpriseVO> page) {
        return R.data(page.setRecords(baseMapper.getEnterpriseMakerIdAll(makerId, enterpriseId, page)));
    }

    @Override
    public R<InvoiceEnterpriseVO> getEnterpriseMakerIdDetail(Long makerId, Long enterpriseId, Long payMakerId) {
        InvoiceEnterpriseVO enterpriseMakerIdDetail = baseMapper.getEnterpriseMakerIdDetail(makerId, enterpriseId, payMakerId);
        PayMakerEntity payMakerEntity = payMakerService.getById(payMakerId);
        MakerTotalInvoiceVO payEnterpriseId = makerTotalInvoiceService.getPayEnterpriseId(payMakerEntity.getPayEnterpriseId());
        if(payEnterpriseId != null){
            return R.fail("抱歉，由于此发票为“汇总代开”的多人发票，你无权限查看");
        }
        return R.data(enterpriseMakerIdDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> upload(PayEnterpriseUploadDTO payEnterpriseUploadDto, Long enterpriseId) throws Exception {

        //判断服务商和商户是否关联
        EnterpriseServiceProviderEntity enterpriseServiceProviderEntity = userClient.queryEnterpriseToServiceProvider(enterpriseId, payEnterpriseUploadDto.getServiceProviderId());
        if (enterpriseServiceProviderEntity == null) {
            return R.fail("服务商和商户未关联");
        }

        //判断工单是否属于商户已完毕工单
        if (payEnterpriseUploadDto.getWorksheetId() != null) {
            WorksheetEntity worksheetEntity = worksheetService.getById(payEnterpriseUploadDto.getWorksheetId());
            if (worksheetEntity == null) {
                return R.fail("工单不存在");
            }

            if (!(worksheetEntity.getEnterpriseId().equals(enterpriseId))) {
                return R.fail("工单不属于商户");
            }

            if (!(WorksheetState.FINISHED.equals(worksheetEntity.getWorksheetState()))) {
                return R.fail("工单未完毕");
            }

            if (!(WorkSheetType.SUBPACKAGE.equals(worksheetEntity.getWorksheetType()))) {
                return R.fail("工单类型有误");
            }

            if (!(payEnterpriseUploadDto.getMakerType().equals(worksheetEntity.getMakerType()))) {
                return R.fail("选择的创客类型与工单创客类型不一致");
            }
        }

        //新建总包支付清单
        PayEnterpriseEntity payEnterpriseEntity = new PayEnterpriseEntity();
        payEnterpriseEntity.setEnterpriseId(enterpriseId);
        payEnterpriseEntity.setServiceProviderId(payEnterpriseUploadDto.getServiceProviderId());
        payEnterpriseEntity.setChargeListUrl(payEnterpriseUploadDto.getChargeListUrl());
        payEnterpriseEntity.setMakerType(payEnterpriseUploadDto.getMakerType());
        payEnterpriseEntity.setWorksheetId(payEnterpriseUploadDto.getWorksheetId());
        payEnterpriseEntity.setPayToPlatformAmount(payEnterpriseUploadDto.getPayToPlatformAmount());
        payEnterpriseEntity.setTotalTaxFee(payEnterpriseUploadDto.getTotalTaxFee());
        payEnterpriseEntity.setTotalMakerNetIncome(payEnterpriseUploadDto.getTotalMakerNetIncome());
        payEnterpriseEntity.setServiceRate(payEnterpriseUploadDto.getServiceRate());
        payEnterpriseEntity.setSourcingAmount(payEnterpriseUploadDto.getSourcingAmount());
        payEnterpriseEntity.setEnterpriseBusinessAnnualFee(payEnterpriseUploadDto.getEnterpriseBusinessAnnualFee());
        payEnterpriseEntity.setIdentifyFee(payEnterpriseUploadDto.getIdentifyFee());
        payEnterpriseEntity.setServiceFee(payEnterpriseUploadDto.getServiceFee());
        payEnterpriseEntity.setMakerNum(payEnterpriseUploadDto.getMakerNum());
        payEnterpriseEntity.setPayMemo(payEnterpriseUploadDto.getPayMemo());
        save(payEnterpriseEntity);

        //支付回单拆分
        String[] split = payEnterpriseUploadDto.getPayReceiptUrls().split(",");
        for (int i = 0; i < split.length; i++) {
            if (StringUtils.isNotBlank(split[i])) {
                PayEnterpriseReceiptEntity payEnterpriseReceiptEntity = new PayEnterpriseReceiptEntity();
                payEnterpriseReceiptEntity.setPayEnterpriseId(payEnterpriseEntity.getId());
                payEnterpriseReceiptEntity.setEnterprisePayReceiptUrl(split[i]);
                payEnterpriseReceiptService.save(payEnterpriseReceiptEntity);
            }
        }

        //根据总包支付清单生成分包
        String path = payEnterpriseEntity.getChargeListUrl();
        String type = path.substring(path.lastIndexOf(".") + 1);
        //根据文件后缀（xls/xlsx）进行判断
        InputStream input = new URL(path).openStream();
        if (!("xls".equals(type)) && !("xlsx".equals(type))) {
            return R.fail("支付清单文件类型有误");
        }

        PayEnterpriseImportListener payEnterpriseImportListener = new PayEnterpriseImportListener(payMakerService, payEnterpriseEntity);
        InputStream inputStream = new BufferedInputStream(input);
        ExcelReaderBuilder builder = EasyExcel.read(inputStream, PayEnterpriseExcel.class, payEnterpriseImportListener);
        builder.doReadAll();

        return R.success("上传支付清单成功");
    }

    @Override
    public R<String> submit(Long payEnterpriseId, Long enterpriseId) {

        PayEnterpriseEntity payEnterpriseEntity = getById(payEnterpriseId);
        if (payEnterpriseEntity == null) {
            return R.fail("总包支付不存在");
        }

        if (!(payEnterpriseEntity.getEnterpriseId().equals(enterpriseId))) {
            return R.fail("总包支付不属于当前商户");
        }

        if (!(EnterprisePayState.TOPAY.equals(payEnterpriseEntity.getPayState())) && !(EnterprisePayState.PAYED.equals(payEnterpriseEntity.getPayState()))) {
            return R.fail("支付清单支付状态有误");
        }

        if (!(PayEnterpriseAuditState.EDITING.equals(payEnterpriseEntity.getAuditState())) && !(PayEnterpriseAuditState.REJECTED.equals(payEnterpriseEntity.getAuditState()))) {
            return R.fail("支付清单审核状态有误");
        }

        //查询是否上传支付回单
        int PayEnterpriseReceipts = payEnterpriseReceiptService.count(Wrappers.<PayEnterpriseReceiptEntity>query().lambda().eq(PayEnterpriseReceiptEntity::getPayEnterpriseId, payEnterpriseId));
        if (PayEnterpriseReceipts > 0) {
            return R.fail("请上传支付回单");
        }

        payEnterpriseEntity.setAuditState(PayEnterpriseAuditState.SUBMITED);
        payEnterpriseEntity.setPayState(EnterprisePayState.PAYED);
        updateById(payEnterpriseEntity);

        return R.success("提交成功");
    }

    @Override
    public R<IPage<PayEnterpriseMakersListVO>> getPayEnterpriseList(Long enterpriseId, Long serviceProviderId, PayEnterpriseDTO payEnterpriseDto, IPage<PayEnterpriseMakersListVO> page) {

        if (payEnterpriseDto.getBeginDate() != null && payEnterpriseDto.getEndDate() != null) {
            if (payEnterpriseDto.getBeginDate().after(payEnterpriseDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getPayEnterpriseList(enterpriseId, serviceProviderId, payEnterpriseDto, page)));
    }

    @Override
    public R<IPage<PayEnterpriseMakerListVO>> getMakers(Long payEnterpriseId, IPage<PayEnterpriseMakerListVO> page) {
        return R.data(page.setRecords(baseMapper.getMakers(payEnterpriseId, page)));
    }

    @Override
    public R<IPage<WorksheetByEnterpriseVO>> getWorksheetByEnterpriseId(Query query, Long enterpriseId, WorkSheetType subpackage, String worksheetNo, String worksheetName) {
        return worksheetService.getWorksheetByEnterpriseId(enterpriseId, WorkSheetType.SUBPACKAGE, worksheetNo, worksheetName, Condition.getPage(query.setDescs("create_time")));
    }

    @Override
    public R findEnterpriseLumpSumInvoice(String invoiceSerialNo, String serviceProviderName, String startTime, String endTime, Long enterpriseId, IPage<EnterpriseLumpSumInvoiceVO> page) {
        IPage<EnterpriseLumpSumInvoiceVO> enterpriseLumpSumInvoiceVOIPage = page.setRecords(baseMapper.findEnterpriseLumpSumInvoice(invoiceSerialNo, serviceProviderName, startTime, endTime, enterpriseId, page));
        List<EnterpriseLumpSumInvoiceVO> records = enterpriseLumpSumInvoiceVOIPage.getRecords();
        for (EnterpriseLumpSumInvoiceVO enterpriseLumpSumInvoiceVO : records) {
            String enterprisePayReceiptUrl = payEnterpriseReceiptService.findEnterprisePayReceiptUrl(enterpriseLumpSumInvoiceVO.getPayEnterpriseId());
            enterpriseLumpSumInvoiceVO.setEnterprisePayReceiptUrl(enterprisePayReceiptUrl);
        }
        return R.data(enterpriseLumpSumInvoiceVOIPage);
    }

    @Override
    public R cancelApply(Long applicationId) {
        InvoiceApplicationEntity invoiceApplicationEntity = invoiceApplicationService.getById(applicationId);
        if (null == invoiceApplicationEntity) {
            return R.fail("申请不存在");
        }
        invoiceApplicationEntity.setApplicationState(ApplicationState.CANCELLED);
        invoiceApplicationService.saveOrUpdate(invoiceApplicationEntity);
        return R.success("取消成功");
    }

    @Override
    public R findPayEnterpriseDetails(Long payEnterpriseId) {
        EnterpriseLumpSumInvoiceVO payEnterpriseDetails = baseMapper.findPayEnterpriseDetails(payEnterpriseId);
        String enterprisePayReceiptUrl = payEnterpriseReceiptService.findEnterprisePayReceiptUrl(payEnterpriseId);
        if (null != enterprisePayReceiptUrl) {
            payEnterpriseDetails.setEnterprisePayReceiptUrl(enterprisePayReceiptUrl);
        }
        KdniaoTrackQueryUtil kdniaoTrackQueryUtil = new KdniaoTrackQueryUtil();
        try {
            String orderTracesByJson = kdniaoTrackQueryUtil.getOrderTracesByJson(payEnterpriseDetails.getExpressCompanyName(), payEnterpriseDetails.getExpressSheetNo());
            payEnterpriseDetails.setKOrderTracesByJson(orderTracesByJson);
        } catch (Exception e) {
            log.info("快鸟接口访问失败");
        }
        return R.data(payEnterpriseDetails);
    }

    @Override
    public R findEnterprisePaymentList(Long enterpriseId, String serviceProviderName, IPage<EnterprisePaymentListVO> page) {
        IPage<EnterprisePaymentListVO> enterprisePaymentListVOIPage = page.setRecords(baseMapper.findEnterprisePaymentList(enterpriseId, serviceProviderName, page));
        List<EnterprisePaymentListVO> records = enterprisePaymentListVOIPage.getRecords();
        for (EnterprisePaymentListVO enterprisePaymentListVO : records) {
            String enterprisePayReceiptUrl = payEnterpriseReceiptService.findEnterprisePayReceiptUrl(enterprisePaymentListVO.getPayEnterpriseId());
            enterprisePaymentListVO.setEnterprisePayReceiptUrl(enterprisePayReceiptUrl);
        }
        return R.data(enterprisePaymentListVOIPage);
    }

    @Override
    public R findEnterpriseSubcontractSummary(Long enterpriseId, String serviceProviderName, IPage<EnterpriseSubcontractInvoiceVO> page) {
        return R.data(page.setRecords(baseMapper.findEnterpriseSubcontractSummary(enterpriseId, serviceProviderName, page)));
    }

    @Override
    public R findEnterpriseSubcontractPortal(Long enterpriseId, String serviceProviderName, IPage<EnterpriseSubcontractPortalVO> page) {
        return R.data(page.setRecords(baseMapper.findEnterpriseSubcontractPortal(enterpriseId, serviceProviderName, page)));
    }

    @Override
    public R findDetailSummary(Long makerTotalInvoiceId,Query query) {
        Map map = new HashMap();
        EnterpriseSubcontractInvoiceVO detailSummary = baseMapper.findDetailSummary(makerTotalInvoiceId);
        map.put("enterpriseSubcontractInvoiceVO", detailSummary);
        PayEnterpriseEntity byId = getById(detailSummary.getPayEnterpriseId());
        if (null == byId) {
           return R.fail("数据错误");
        }
        List<PayMakerListVO> PayMakerListVOs = baseMapper.getPayMakerListByPayEnterprise(byId.getId(), Condition.getPage(query.setDescs("create_time")));
        map.put("payMakerListVOs", PayMakerListVOs);
        return R.data(map);
    }

    @Override
    public R findDetailSubcontractPortal(Long makerInvoiceId,Query query) {
        EnterpriseSubcontractPortalVO detailSummary = baseMapper.findDetailSubcontractPortal(makerInvoiceId);
        Map map = new HashMap();
        map.put("EnterpriseSubcontractPortalVO", detailSummary);
        PayEnterpriseEntity byId = getById(detailSummary.getPayEnterpriseId());
        if (null == byId) {
            R.fail("数据错误");
        }
        List<PayMakerListVO> PayMakerListVOs = baseMapper.getPayMakerListByPayEnterprise(byId.getId(), Condition.getPage(query.setDescs("create_time")));
        map.put("payMakerListVOs", PayMakerListVOs);
        return R.data(map);
    }

    @Override
    public R<IPage<PayMakerListVO>> getPayMakerListByPayEnterprise(Long payEnterpriseId, IPage<PayMakerListVO> page) {
        return R.data(page.setRecords(baseMapper.getPayMakerListByPayEnterprise(payEnterpriseId, page)));
    }

    @Override
    public R<String> audit(Long payEnterpriseId, Long serviceProviderId, PayEnterpriseAuditState auditState, MakerInvoiceType makerInvoiceType) {

        PayEnterpriseEntity payEnterpriseEntity = getById(payEnterpriseId);
        if (payEnterpriseEntity == null) {
            return R.fail("总包不存在");
        }

        if (!(payEnterpriseEntity.getServiceProviderId().equals(serviceProviderId))) {
            return R.fail("总包不属于当前服务商");
        }

        if (!(EnterprisePayState.PAYED.equals(payEnterpriseEntity.getPayState()))) {
            return R.fail("总包支付状态有误");
        }

        if (!(PayEnterpriseAuditState.SUBMITED.equals(payEnterpriseEntity.getAuditState()))) {
            return R.fail("总包审核状态有误");
        }

        if (!(PayEnterpriseAuditState.APPROVED.equals(auditState)) && !(PayEnterpriseAuditState.REJECTED.equals(auditState))) {
            return R.fail("审核状态有误");
        }

        if (PayEnterpriseAuditState.APPROVED.equals(auditState)) {
            if (makerInvoiceType == null) {
                return R.fail("请选择创客发票开票类别");
            }

            payEnterpriseEntity.setMakerInvoiceType(makerInvoiceType);
            payEnterpriseEntity.setPayState(EnterprisePayState.CONFIRMPAY);
        }
        payEnterpriseEntity.setAuditState(auditState);
        updateById(payEnterpriseEntity);

        return R.success("审核成功");
    }

    @Override
    public R<IPage<PayEnterpriseMakersListVO>> getPayEnterprisesByEnterprisesServiceProvider(Long enterpriseId, Long serviceProviderId, PayEnterpriseDTO payEnterpriseDto, IPage<PayEnterpriseMakersListVO> page) {

        if (payEnterpriseDto.getBeginDate() != null && payEnterpriseDto.getEndDate() != null) {
            if (payEnterpriseDto.getBeginDate().after(payEnterpriseDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getPayEnterprisesByEnterprisesServiceProvider(enterpriseId, serviceProviderId, payEnterpriseDto, page)));
    }

    @Override
    public R<TransactionVO> transactionByEnterprise(Long enterpriseId) {
        return R.data(baseMapper.transactionByEnterprise(enterpriseId));
    }

    @Override
    public R<TransactionVO> transactionByServiceProvider(Long serviceProviderId) {
        return R.data(baseMapper.transactionByServiceProvider(serviceProviderId));
    }

    @Override
    public R<YearTradeVO> queryTotalSubYearTradeByEnterprise(Long enterpriseId) {
        return R.data(baseMapper.queryTotalSubYearTradeByEnterprise(enterpriseId));
    }

    @Override
    public R<YearTradeVO> queryTotalSubYearTradeByServiceProvider(Long serviceProviderId) {
        return R.data(baseMapper.queryTotalSubYearTradeByServiceProvider(serviceProviderId));
    }

    @Override
    public R<MonthTradeVO> queryTotalSubMonthTradeByEnterprise(Long enterpriseId) {
        return R.data(baseMapper.queryTotalSubMonthTradeByEnterprise(enterpriseId));
    }

    @Override
    public R<MonthTradeVO> queryTotalSubMonthTradeByServiceProvider(Long serviceProviderId) {
        return R.data(baseMapper.queryTotalSubMonthTradeByServiceProvider(serviceProviderId));
    }

    @Override
    public R<WeekTradeVO> queryTotalSubWeekTradeByEnterprise(Long enterpriseId) {
        return R.data(baseMapper.queryTotalSubWeekTradeByEnterprise(enterpriseId));
    }

    @Override
    public R<WeekTradeVO> queryTotalSubWeekTradeByServiceProvider(Long serviceProviderId) {
        return R.data(baseMapper.queryTotalSubWeekTradeByServiceProvider(serviceProviderId));
    }

    @Override
    public R<DayTradeVO> queryTotalSubDayTradeByEnterprise(Long enterpriseId) {
        return R.data(baseMapper.queryTotalSubDayTradeByEnterprise(enterpriseId));
    }

    @Override
    public R<DayTradeVO> queryTotalSubDayTradeByServiceProvider(Long serviceProviderId) {
        return R.data(baseMapper.queryTotalSubDayTradeByServiceProvider(serviceProviderId));
    }

    @Override
    public R getServiceLumpSumInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime,InvoiceState companyInvoiceState, IPage<InvoiceServiceLumpVO> page) {
        return R.data(page.setRecords(baseMapper.getServiceLumpSumInvoice(serviceProviderId,enterpriseName,startTime,endTime,companyInvoiceState,page)));
    }

    @Override
    public R getServiceLumpSumInvoiceDetails(Long payEnterpriseId) {
        Map map = new HashMap();
        List<InvoiceServiceLumpDetailsVO> lumpSumInvoiceDetails = baseMapper.getServiceLumpSumInvoiceDetails(payEnterpriseId);
        if(lumpSumInvoiceDetails.size() > 0){
            map.put("lumpSumInvoiceDetails",lumpSumInvoiceDetails.get(0));
        }else{
            R.fail("商户支付清单不存在！！！");
        }
        String enterprisePayReceiptUrl = payEnterpriseReceiptService.findEnterprisePayReceiptUrl(payEnterpriseId);
        lumpSumInvoiceDetails.get(0).setEnterprisePayReceiptUrl(enterprisePayReceiptUrl);
        String expressCompanyName = lumpSumInvoiceDetails.get(0).getExpressCompanyName();
        String expressSheetNo = lumpSumInvoiceDetails.get(0).getExpressSheetNo();
        KdniaoTrackQueryUtil kdniaoTrackQueryUtil = new KdniaoTrackQueryUtil();
        String orderTracesByJson = "";
        try {
            if (!StringUtil.isBlank(expressCompanyName) && !StringUtil.isBlank(expressSheetNo)) {
                orderTracesByJson = kdniaoTrackQueryUtil.getOrderTracesByJson(expressCompanyName, expressSheetNo);
                map.put("orderTracesByJson",orderTracesByJson);
            }else{
                map.put("orderTracesByJson","");
            }
        } catch (Exception e) {
            log.error("查询物流错误", e);
        }
        return R.data(map);
    }

    @Override
    @Transactional
    public R saveServiceLumpSumInvoice(Long serviceProviderId, Long payEnterpriseId,String serviceProviderName, Long applicationId, String companyInvoiceUrl, String expressSheetNo, String expressCompanyName,String invoiceDesc) {
        PayEnterpriseEntity byId = getById(payEnterpriseId);
        //EnterpriseEntity enterpriseById = userClient.getEnterpriseById(byId.getEnterpriseId());

        PlatformInvoiceEntity platformInvoiceEntity = new PlatformInvoiceEntity();
        platformInvoiceEntity.setApplicationId(applicationId);
        platformInvoiceEntity.setInvoicePrintDate(new Date());
        //价税合计
        platformInvoiceEntity.setInvoiceTotalAmount(new BigDecimal("0"));
        platformInvoiceEntity.setInvoiceNumbers(1);
        if(null == serviceProviderName){
            platformInvoiceEntity.setInvoicePrintPerson("平台");
        }else{
            platformInvoiceEntity.setInvoicePrintPerson(serviceProviderName);
        }

        platformInvoiceEntity.setExpressSheetNo(expressSheetNo);
        platformInvoiceEntity.setExpressCompanyName(expressCompanyName);
        platformInvoiceEntity.setInvoiceDesc(invoiceDesc);
        platformInvoiceService.save(platformInvoiceEntity);
        PlatformInvoicePayListEntity platformInvoicePayListEntity = new PlatformInvoicePayListEntity();
        platformInvoicePayListEntity.setPayEnterpriseId(payEnterpriseId);
        platformInvoicePayListEntity.setInvoicePrintId(platformInvoiceEntity.getId());
        platformInvoicePayListService.save(platformInvoicePayListEntity);
        PlatformInvoiceListEntity platformInvoiceListEntity = new PlatformInvoiceListEntity();
        platformInvoiceListEntity.setInvoicePrintId(platformInvoiceEntity.getId());
        //发票代码
        platformInvoiceListEntity.setInvoiceTypeNo(UUID.randomUUID().toString());
        //发票号码
        platformInvoiceListEntity.setInvoiceSerialNo(UUID.randomUUID().toString());
        platformInvoiceListEntity.setInvoiceDatetime(new Date());
        //价税合计
        platformInvoiceListEntity.setTotalAmount(new BigDecimal("0"));
        //金额合计
        platformInvoiceListEntity.setSalesAmount(new BigDecimal("0"));
        //税额合计
        platformInvoiceListEntity.setTaxAmount(new BigDecimal("0"));
        if(null == serviceProviderName){
            platformInvoiceListEntity.setInvoicePerson("平台");
        }else{
            platformInvoiceListEntity.setInvoicePerson(serviceProviderName);
        }
        //销售方名称
        platformInvoiceListEntity.setSaleCompany("商户");
        platformInvoiceListEntity.setCompanyInvoiceUrl(companyInvoiceUrl);
        platformInvoiceListEntity.setCompanyVoiceUploadDatetime(new Date());
        platformInvoiceListService.save(platformInvoiceListEntity);
        //更新商户支付清单的总包开票状态
        byId.setCompanyInvoiceState(InvoiceState.OPENED);
        saveOrUpdate(byId);
        return R.success("操作成功");
    }

    @Override
    public R getSubcontractInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, IPage<InvoiceServiceSubVO> page) {
        return R.data(page.setRecords(baseMapper.getSubcontractInvoice(serviceProviderId,enterpriseName,startTime,endTime,page)));
    }

    @Override
    public R getSubcontractInvoiceDetails(Long payEnterpriseId) {
        return R.data(baseMapper.getSubcontractInvoiceDetails(payEnterpriseId));
    }

    @Override
    @Transactional
    public R saveSummaryInvoice(Long serviceProviderId, Long payEnterpriseId, String serviceProviderName, String invoiceTypeNo, String invoiceSerialNo, String invoiceCategory, String companyInvoiceUrl, String makerTaxUrl, String makerTaxListUrl) {
        PayEnterpriseEntity byId = getById(payEnterpriseId);
        byId.setSubcontractingInvoiceState(InvoiceState.OPENED);
        MakerTotalInvoiceEntity makerTotalInvoiceEntity = new MakerTotalInvoiceEntity();
        makerTotalInvoiceEntity.setPayEnterpriseId(payEnterpriseId);
        makerTotalInvoiceEntity.setInvoiceTypeNo(invoiceTypeNo);
        makerTotalInvoiceEntity.setInvoiceSerialNo(invoiceSerialNo);
        makerTotalInvoiceEntity.setInvoiceDatetime(new Date());
        makerTotalInvoiceEntity.setInvoiceCategory(invoiceCategory);
        makerTotalInvoiceEntity.setTotalAmount(new BigDecimal("0"));
        makerTotalInvoiceEntity.setSalesAmount(new BigDecimal("0"));
        makerTotalInvoiceEntity.setTaxAmount(new BigDecimal("0"));
        if(null == serviceProviderName){
            makerTotalInvoiceEntity.setInvoicePerson("平台");
            makerTotalInvoiceEntity.setSaleCompany("平台");
        }else{
            makerTotalInvoiceEntity.setInvoicePerson(serviceProviderName);
            makerTotalInvoiceEntity.setSaleCompany(serviceProviderName);
        }
        makerTotalInvoiceEntity.setCompanyInvoiceUrl(companyInvoiceUrl);
        makerTotalInvoiceEntity.setCompanyVoiceUploadDatetime(new Date());
        makerTotalInvoiceEntity.setMakerTaxUrl(makerTaxUrl);
        makerTotalInvoiceEntity.setMakerTaxListUrl(makerTaxListUrl);
        makerTotalInvoiceService.save(makerTotalInvoiceEntity);
        saveOrUpdate(byId);
        return R.success("汇总代开成功");
    }

    @Override
    public R applyPortalSignInvoice(Long payEnterpriseId) {
        Map map = new HashMap();
        InvoiceServiceSubDetailsVO subcontractInvoiceDetail = baseMapper.getSubcontractInvoiceDetails(payEnterpriseId);
        List<PayMakerVO> payMakerList = payMakerService.getPayEnterpriseId(payEnterpriseId);
        map.put("payMakerList",payMakerList);
        map.put("subcontractInvoiceDetail",subcontractInvoiceDetail);
        return R.data(map);
    }

    @Override
    @Transactional
    public R savePortalSignInvoice(Long serviceProviderId, Long payEnterpriseId, String payMakers,String serviceProviderName) {
        JSONArray payMakerArray;
        payMakerArray = new JSONArray(payMakers);
        for (int i = 0;i < payMakerArray.length() ; i++) {
            String voiceTypeNo = payMakerArray.getJSONObject(i).get("voiceTypeNo").toString();
            String voiceSerialNo = payMakerArray.getJSONObject(i).get("voiceSerialNo").toString();
            String voiceCategory = payMakerArray.getJSONObject(i).get("voiceCategory").toString();
            String helpMakeOrganationName = payMakerArray.getJSONObject(i).get("helpMakeOrganationName").toString();
            String helpMakeCompany = payMakerArray.getJSONObject(i).get("helpMakeCompany").toString();
            String makerVoiceUrl = payMakerArray.getJSONObject(i).get("makerVoiceUrl").toString();
            String helpMakeTaxNo = payMakerArray.getJSONObject(i).get("helpMakeTaxNo").toString();
            String payMakerId = payMakerArray.getJSONObject(i).get("payMakerId").toString();
            String makerTaxRecord = payMakerArray.getJSONObject(i).get("makerTaxRecord").toString();
            String taxAmount = payMakerArray.getJSONObject(i).get("taxAmount").toString();
            PayMakerEntity byId = payMakerService.getById(payMakerId);
            if(null == byId){
                return R.fail("创客支付明细不存在");
            }
            String voicePerson = userClient.queryMakerById(byId.getMakerId()).getName();
            BigDecimal makerNeIncome = byId.getMakerNeIncome();
            BigDecimal salesAmount = makerNeIncome.divide(new BigDecimal("1").add(new BigDecimal("0.03")),2);
            MakerInvoiceEntity makerInvoiceEntity = new MakerInvoiceEntity(byId.getId(),voiceTypeNo,voiceSerialNo,new Date(),voiceCategory,byId.getMakerNeIncome(),salesAmount,new BigDecimal(taxAmount),voicePerson,helpMakeOrganationName,helpMakeOrganationName,helpMakeCompany,helpMakeTaxNo,makerVoiceUrl,new Date());
            makerInvoiceService.save(makerInvoiceEntity);
            if(StringUtil.isNotBlank(makerTaxRecord)){
                JSONArray makerTaxRecordArray = new JSONArray(payMakers);
                for (int j = 0;j < makerTaxRecordArray.length() ; j++) {
                    String voiceTaxTypeNo = payMakerArray.getJSONObject(i).get("voiceTaxTypeNo").toString();
                    String voiceTaxSerialNo = payMakerArray.getJSONObject(i).get("voiceTaxSerialNo").toString();
                    String makerTaxUrl = payMakerArray.getJSONObject(i).get("makerTaxUrl").toString();
                    String makerTaxAmount = payMakerArray.getJSONObject(i).get("makerTaxAmount").toString();
                    MakerTaxRecordEntity makerTaxRecordEntity = new MakerTaxRecordEntity(byId.getId(),voiceTaxTypeNo,voiceTaxSerialNo,new BigDecimal(makerTaxAmount),byId.getMakerNeIncome(),salesAmount,new BigDecimal(taxAmount),voicePerson,helpMakeOrganationName,helpMakeOrganationName,makerTaxUrl,new Date(),new Date());
                    makerTaxRecordService.save(makerTaxRecordEntity);
                }
            }
        }
        PayEnterpriseEntity byId = getById(payEnterpriseId);
        byId.setSubcontractingInvoiceState(InvoiceState.OPENED);
        saveOrUpdate(byId);
        return R.success("操作成功");
    }

    @Override
    public R getServiceSummaryInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime,IPage<InvoiceServiceSubVO> page) {
        return R.data(page.setRecords(baseMapper.getServiceSummaryInvoice( serviceProviderId,  enterpriseName,  startTime,  endTime, page)));
    }

    @Override
    public R getSummaryInvoiceDetails(Long payEnterpriseId) {
        Map map = new HashMap();
        InvoiceServiceSubDetailsVO subcontractInvoiceDetail = baseMapper.getSubcontractInvoiceDetails(payEnterpriseId);
        MakerTotalInvoiceVO makerTotalInvoiceVO = makerTotalInvoiceService.getPayEnterpriseId(payEnterpriseId);
        map.put("subcontractInvoiceDetail",subcontractInvoiceDetail);
        map.put("makerTotalInvoiceVO",makerTotalInvoiceVO);
        return R.data(map);
    }

    @Override
    public R getServicePortalSignInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, IPage<InvoiceServiceSubVO> page) {
        return R.data(page.setRecords(baseMapper.getServicePortalSignInvoice( serviceProviderId,  enterpriseName,  startTime,  endTime, page)));
    }

    @Override
    public R getServicePortalSignInvoiceDetails(Long payEnterpriseId) {
        Map map = new HashMap();
        InvoiceServiceSubDetailsVO subcontractInvoiceDetail = baseMapper.getSubcontractInvoiceDetails(payEnterpriseId);
        List<PayMakerVO> payMakerList = payMakerService.getPayEnterprise(payEnterpriseId);
        map.put("payMakerList",payMakerList);
        map.put("subcontractInvoiceDetail",subcontractInvoiceDetail);
        return R.data(map);
    }

    /**
     *查询当前相关局所有匹配的服务商交易情况数据
     * @param bureauId
     * @return
     */
    @Override
    public R<TransactionVO> transactionByBureauServiceProvider(Long bureauId) {
        return R.data(baseMapper.transactionByBureauServiceProvider(bureauId));
    }

    /**
     * 查询相关局匹配的服务商基本信息及交易金额
     * @param bureauId
     * @param page
     * @return
     */
    @Override
    public R<IPage<TransactionByBureauServiceProviderInfoVO>> transactionByBureauServiceProviderInfo(Long bureauId, IPage<TransactionByBureauServiceProviderInfoVO> page) {
        return R.data(page.setRecords(baseMapper.transactionByBureauServiceProviderInfo(bureauId,page)));
    }

    @Override
    public R<AgentMainTransactionVO> transactionByAgentMainId(Long agentMainId) {
        AgentMainTransactionVO agentMainTransactionVO = baseMapper.getTransactionByAgentMainId(agentMainId);
        return R.data(agentMainTransactionVO);
    }

    @Override
    public R<IPage<AdminAgentMainServiceProviderListVO>> getCooperativeServiceProvider(IPage<AdminAgentMainServiceProviderListVO> page,Long agentMainId) {
        return R.data(page.setRecords(baseMapper.getAgentMainServiceProviderList(agentMainId, page)));
    }

    @Override
    public R<AgentMainTransactionVO> allTransaction() {
        return R.data(baseMapper.getAllTransaction());
    }

    @Override
    public R<IPage<PartnerServiceProviderListVO>> getPartnerAllServiceProvider(IPage<PartnerServiceProviderListVO> page) {
        return R.data(page.setRecords(baseMapper.getPartnerAllServiceProvider(page)));
    }

    @Override
    public R queryTotalInvoiceListEnterprise(Long enterpriseId,String serviceProviderName,IPage<TotalInvoiceListEnterVO> page) {
        return R.data(page.setRecords(baseMapper.queryTotalInvoiceListEnterprise(enterpriseId,serviceProviderName,page)));
    }

    @Override
    public R queryTotalInvoiceListEnterpriseApplyDetails(Long invoiceApplicationId,Long enterpriseId) {
        Map map = new HashMap();
        EnterpriseEntity enterpriseEntity = userClient.queryEnterpriseById(enterpriseId);
        List<InvoiceApplicationPayListEntity> applicationList = invoiceApplicationPayListService.getApplicationId(invoiceApplicationId);

        InvoiceApplicationEntity invoiceApplicationEntity = invoiceApplicationService.getById(invoiceApplicationId);
        if(null == applicationList || null == invoiceApplicationEntity){
            return R.fail("参数有误");
        }
        String payEnterpriseIds = "";
        String enterprisePayReceiptUrl = "";
        String worksheetIds = "";
        Long serviceProviderId = null;
        for (InvoiceApplicationPayListEntity invoiceApplicationPayListEntity : applicationList) {
            Long payEnterpriseId = invoiceApplicationPayListEntity.getPayEnterpriseId();
            payEnterpriseIds +=payEnterpriseId+",";
            enterprisePayReceiptUrl += payEnterpriseReceiptService.findEnterprisePayReceiptUrl(payEnterpriseId);
            PayEnterpriseEntity byId = getById(payEnterpriseId);
            serviceProviderId = byId.getServiceProviderId();
            if(null!= byId && null != byId.getWorksheetId()){
                worksheetIds += byId.getWorksheetId() + ",";
            }
        }
        ServiceProviderEntity serviceProviderEntity = userClient.queryServiceProviderById(serviceProviderId);
        map.put("enterpriseName",enterpriseEntity.getEnterpriseName());
        map.put("serviceProviderName",serviceProviderEntity.getServiceProviderName());
        map.put("invoiceApplicationId",invoiceApplicationId);
        map.put("enterpriseId",enterpriseId);
        map.put("payEnterpriseIds",payEnterpriseIds);
        map.put("enterprisePayReceiptUrl",enterprisePayReceiptUrl);
        map.put("worksheetIds",worksheetIds);
        map.put("applicationState",invoiceApplicationEntity.getApplicationState());
        map.put("companyInvoiceURL","");
        map.put("invoiceCreateTiem","");
        map.put("enterprisePayReceiptUrl",enterprisePayReceiptUrl);
        map.put("orderTracesByJson","");
        return R.data(map);
    }

    @Override
    public R queryTotalInvoiceListEnterpriseInvoiceDetails(Long invoicePrintId, Long enterpriseId) {
        Map map = new HashMap();
        EnterpriseEntity enterpriseEntity = userClient.queryEnterpriseById(enterpriseId);
        List<PlatformInvoicePayListEntity> invoicePrintIdList = platformInvoicePayListService.findInvoicePrintId(invoicePrintId);
        PlatformInvoiceEntity platformInvoiceEntity = platformInvoiceService.getById(invoicePrintId);
        String payEnterpriseIds = "";
        String enterprisePayReceiptUrl = "";
        String worksheetIds = "";
        Long serviceProviderId = null;
        for (PlatformInvoicePayListEntity platformInvoicePayListEntity : invoicePrintIdList) {
            Long payEnterpriseId = platformInvoicePayListEntity.getPayEnterpriseId();
            payEnterpriseIds +=payEnterpriseId+",";
            enterprisePayReceiptUrl += payEnterpriseReceiptService.findEnterprisePayReceiptUrl(payEnterpriseId);
            PayEnterpriseEntity byId = getById(payEnterpriseId);
            serviceProviderId = byId.getServiceProviderId();
            if(null!= byId && null != byId.getWorksheetId()){
                worksheetIds += byId.getWorksheetId() + ",";
            }
        }
        ServiceProviderEntity serviceProviderEntity = userClient.queryServiceProviderById(serviceProviderId);
        map.put("enterpriseName",enterpriseEntity.getEnterpriseName());
        map.put("serviceProviderName",serviceProviderEntity.getServiceProviderName());
        map.put("invoiceApplicationId",enterpriseId);
        map.put("enterpriseId",enterpriseId);
        map.put("payEnterpriseIds",payEnterpriseIds);
        map.put("enterprisePayReceiptUrl",enterprisePayReceiptUrl);
        map.put("worksheetIds",worksheetIds);
        map.put("applicationState",ApplicationState.ISSUEDINFULL);
        List<PlatformInvoiceListEntity> platformInvoiceListEntitys = platformInvoiceListService.findInvoicePrintId(invoicePrintId);
        String companyInvoiceURLs = "";
        for (PlatformInvoiceListEntity platformInvoiceListEntity : platformInvoiceListEntitys) {
            companyInvoiceURLs += platformInvoiceListEntity.getCompanyInvoiceUrl();
        }
        map.put("companyInvoiceURL",companyInvoiceURLs);
        map.put("invoiceCreateTiem",platformInvoiceEntity.getCreateTime());
        map.put("enterprisePayReceiptUrl",enterprisePayReceiptUrl);
        KdniaoTrackQueryUtil kdniaoTrackQueryUtil = new KdniaoTrackQueryUtil();
        String orderTracesByJson = "";
        try{
            orderTracesByJson = kdniaoTrackQueryUtil.getOrderTracesByJson(platformInvoiceEntity.getExpressCompanyName(), platformInvoiceEntity.getExpressSheetNo());
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        map.put("orderTracesByJson",orderTracesByJson);
        return R.data(map);
    }

    @Override
    public R queryRelationEnterpriseService(Long enterpriseId, String serviceProviderName, IPage<RelationEnterpriseServiceVO> page) {
        return R.data(page.setRecords(baseMapper.queryRelationEnterpriseService(enterpriseId,serviceProviderName,page)));
    }

    @Override
    public R queryEnterpriseServicePayList(Long enterpriseId, Long serviceProviderId,IPage<EnterpriseServicePayListVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseServicePayList(enterpriseId,serviceProviderId,page)));
    }

}
