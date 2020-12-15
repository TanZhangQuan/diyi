package com.lgyun.system.order.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.KdniaoTrackQueryUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.*;
import com.lgyun.system.order.entity.*;
import com.lgyun.system.order.excel.PayEnterpriseExcel;
import com.lgyun.system.order.excel.PayEnterpriseImportListener;
import com.lgyun.system.order.excel.PayEnterpriseReadListener;
import com.lgyun.system.order.mapper.PayEnterpriseMapper;
import com.lgyun.system.order.service.*;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.dto.PayEnterpriseListSimpleDTO;
import com.lgyun.system.user.feign.IUserClient;
import com.lgyun.system.order.vo.TotalCrowdTradeListVO;
import com.lgyun.system.user.vo.TransactionVO;
import fr.opensagres.xdocreport.document.json.JSONArray;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-07-17 20:01:13
 */
@Slf4j
@Service
@AllArgsConstructor
public class PayEnterpriseServiceImpl extends BaseServiceImpl<PayEnterpriseMapper, PayEnterpriseEntity> implements IPayEnterpriseService {

    private IUserClient userClient;
    private IPayMakerService payMakerService;
    private IWorksheetService worksheetService;
    private IMakerInvoiceService makerInvoiceService;
    private IMakerTaxRecordService makerTaxRecordService;
    private IAcceptPaysheetService acceptPaysheetService;
    private IPlatformInvoiceService platformInvoiceService;
    private IMakerTotalInvoiceService makerTotalInvoiceService;
    private IInvoiceApplicationService invoiceApplicationService;
    private IPlatformInvoiceListService platformInvoiceListService;
    private IPayEnterpriseReceiptService payEnterpriseReceiptService;
    private IPlatformInvoicePayListService platformInvoicePayListService;
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
        if (payEnterpriseId != null) {
            return R.fail("抱歉，由于此发票为“汇总代开”的多人发票，你无权限查看");
        }
        return R.data(enterpriseMakerIdDetail);
    }

    @Override
    public R<List<PayEnterpriseExcel>> readPayEnterpriseExcel(String chargeListUrl) throws Exception {

        InputStream inputStream = new URL(chargeListUrl).openStream();
        //根据总包支付清单生成分包
        PayEnterpriseReadListener payEnterpriseReadListener = new PayEnterpriseReadListener();
        ExcelReader excelReader = EasyExcelFactory.read(inputStream, PayEnterpriseExcel.class, payEnterpriseReadListener).headRowNumber(1).build();
        excelReader.readAll();
        List<PayEnterpriseExcel> payEnterpriseExcelList = payEnterpriseReadListener.getList();
        excelReader.finish();

        return R.data(payEnterpriseExcelList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createOrUpdatePayEnterprise(PayEnterpriseCreateOrUpdateDTO payEnterpriseCreateOrUpdateDto, Long enterpriseId) throws Exception {

        //判断服务商和商户是否关联
        int CooperateNum = userClient.queryCountByEnterpriseIdAndServiceProviderId(enterpriseId, payEnterpriseCreateOrUpdateDto.getServiceProviderId(), CooperateStatus.COOPERATING);
        if (CooperateNum <= 0) {
            return R.fail("服务商和商户未关联");
        }

        //判断工单是否属于商户已完毕工单
        if (payEnterpriseCreateOrUpdateDto.getWorksheetId() != null) {
            WorksheetEntity worksheetEntity = worksheetService.getById(payEnterpriseCreateOrUpdateDto.getWorksheetId());
            if (worksheetEntity == null) {
                return R.fail("工单不存在");
            }

            if (!(worksheetEntity.getEnterpriseId().equals(enterpriseId))) {
                return R.fail("工单不属于商户");
            }

            if (!(WorksheetState.FINISHED.equals(worksheetEntity.getWorksheetState()))) {
                return R.fail("工单未完毕");
            }

            if (!(WorksheetType.SUBPACKAGE.equals(worksheetEntity.getWorksheetType()))) {
                return R.fail("工单类型有误");
            }

            if (!(payEnterpriseCreateOrUpdateDto.getMakerType().equals(worksheetEntity.getMakerType()))) {
                return R.fail("选择的创客类型与工单创客类型不一致");
            }
        }

        //判断新建还是编辑
        PayEnterpriseEntity payEnterpriseEntity;
        if (payEnterpriseCreateOrUpdateDto.getPayenterpriseId() == null) {
            payEnterpriseEntity = new PayEnterpriseEntity();
            payEnterpriseEntity.setEnterpriseId(enterpriseId);
            payEnterpriseEntity.setPayConfirmDateTime(new Date());
            BeanUtils.copyProperties(payEnterpriseCreateOrUpdateDto, payEnterpriseEntity);
            saveOrUpdate(payEnterpriseEntity);

            //根据总包支付清单生成分包
            PayEnterpriseImportListener payEnterpriseImportListener = new PayEnterpriseImportListener(payMakerService, payEnterpriseEntity.getId(), payEnterpriseEntity.getMakerType(), enterpriseId);
            InputStream inputStream = new URL(payEnterpriseCreateOrUpdateDto.getChargeListUrl()).openStream();
            ExcelReaderBuilder builder = EasyExcel.read(inputStream, PayEnterpriseExcel.class, payEnterpriseImportListener);
            builder.doReadAll();

        } else {
            payEnterpriseEntity = getById(payEnterpriseCreateOrUpdateDto.getPayenterpriseId());
            if (payEnterpriseEntity == null) {
                return R.fail("总包支付清单不存在");
            }

            if (!(payEnterpriseEntity.getEnterpriseId().equals(enterpriseId))) {
                return R.fail("总包支付清单不属于商户");
            }

            if (!(PayEnterpriseAuditState.EDITING.equals(payEnterpriseEntity.getAuditState())) && !(PayEnterpriseAuditState.REJECTED.equals(payEnterpriseEntity.getAuditState()))) {
                return R.fail("非编辑或已驳回状态的总包支付清单不可修改");
            }

            //总包支付清单修改处理
            if (!(payEnterpriseCreateOrUpdateDto.getChargeListUrl().equals(payEnterpriseEntity.getChargeListUrl()))) {

                //删除旧的分包
                payMakerService.deleteByPayEnterpriseId(payEnterpriseEntity.getId());

                //删除旧的总包支付回单
                payEnterpriseReceiptService.deleteByPayEnterpriseId(payEnterpriseEntity.getId());

                //删除旧的总包交付支付验收单
                acceptPaysheetService.deleteAcceptPaysheet(payEnterpriseEntity.getId());

                payEnterpriseEntity.setPayConfirmDateTime(new Date());
                BeanUtils.copyProperties(payEnterpriseCreateOrUpdateDto, payEnterpriseEntity);
                saveOrUpdate(payEnterpriseEntity);

                //根据总包支付清单生成分包
                PayEnterpriseImportListener payEnterpriseImportListener = new PayEnterpriseImportListener(payMakerService, payEnterpriseEntity.getId(), payEnterpriseEntity.getMakerType(), enterpriseId);
                InputStream inputStream = new URL(payEnterpriseCreateOrUpdateDto.getChargeListUrl()).openStream();
                ExcelReaderBuilder builder = EasyExcel.read(inputStream, PayEnterpriseExcel.class, payEnterpriseImportListener);
                builder.doReadAll();

            } else {

                BeanUtils.copyProperties(payEnterpriseCreateOrUpdateDto, payEnterpriseEntity);
                saveOrUpdate(payEnterpriseEntity);

            }
        }

        //支付回单拆分
        String[] split = payEnterpriseCreateOrUpdateDto.getEnterprisePayReceiptUrls().split(",");
        for (int i = 0; i < split.length; i++) {
            if (StringUtils.isNotBlank(split[i])) {
                PayEnterpriseReceiptEntity payEnterpriseReceiptEntity = new PayEnterpriseReceiptEntity();
                payEnterpriseReceiptEntity.setPayEnterpriseId(payEnterpriseEntity.getId());
                payEnterpriseReceiptEntity.setEnterprisePayReceiptUrl(split[i]);
                payEnterpriseReceiptService.save(payEnterpriseReceiptEntity);
            }
        }

        return R.success("上传支付清单成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> submit(Long payEnterpriseId, Long enterpriseId) {

        PayEnterpriseEntity payEnterpriseEntity = getById(payEnterpriseId);
        if (payEnterpriseEntity == null) {
            return R.fail("总包支付清单不存在");
        }

        if (!(payEnterpriseEntity.getEnterpriseId().equals(enterpriseId))) {
            return R.fail("总包支付清单不属于当前商户");
        }

        if (!(PayEnterpriseAuditState.EDITING.equals(payEnterpriseEntity.getAuditState())) && !(PayEnterpriseAuditState.REJECTED.equals(payEnterpriseEntity.getAuditState()))) {
            return R.fail("非编辑中或已驳回状态的总包支付清单不可提交");
        }

        payEnterpriseEntity.setAuditState(PayEnterpriseAuditState.SUBMITED);
        updateById(payEnterpriseEntity);

        return R.success("提交成功");
    }

    @Override
    public R<IPage<PayEnterpriseListVO>> getPayEnterpriseList(Long enterpriseId, Long serviceProviderId, PayEnterpriseDTO payEnterpriseDto, IPage<PayEnterpriseListVO> page) {

        if (payEnterpriseDto.getBeginDate() != null && payEnterpriseDto.getEndDate() != null) {
            if (payEnterpriseDto.getBeginDate().after(payEnterpriseDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getPayEnterpriseList(enterpriseId, serviceProviderId, payEnterpriseDto, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> cancelApply(Long applicationId) {
        InvoiceApplicationEntity invoiceApplicationEntity = invoiceApplicationService.getById(applicationId);
        if (null == invoiceApplicationEntity) {
            return R.fail("申请不存在");
        }
        invoiceApplicationEntity.setApplicationState(ApplicationState.CANCELLED);
        invoiceApplicationService.saveOrUpdate(invoiceApplicationEntity);
        return R.success("取消成功");
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
    public R findDetailSummary(Long makerTotalInvoiceId) {
        Map map = new HashMap();
        EnterpriseSubcontractInvoiceVO detailSummary = baseMapper.findDetailSummary(makerTotalInvoiceId);
        PayEnterpriseEntity byId = getById(detailSummary.getPayEnterpriseId());
        String enterprisePayReceiptUrl = payEnterpriseReceiptService.findEnterprisePayReceiptUrl(byId.getId());
        detailSummary.setEnterprisePayReceiptUrl(enterprisePayReceiptUrl);
        if (null == byId) {
            return R.fail("数据错误");
        }
        map.put("enterpriseSubcontractInvoiceVO", detailSummary);
        Query query = new Query();
        query.setCurrent(1);
        query.setSize(100);
        List<PayMakerListInvoiceVO> PayMakerListVOs = baseMapper.queryPayMakerListInvoice(byId.getId(), Condition.getPage(query.setDescs("t1.create_time")));
        map.put("payMakerListVOs", PayMakerListVOs);

        return R.data(map);
    }

    @Override
    public R findDetailSubcontractPortal(Long makerInvoiceId) {
        EnterpriseSubcontractPortalVO detailSummary = baseMapper.findDetailSubcontractPortal(makerInvoiceId);
        Map map = new HashMap();
        PayEnterpriseEntity byId = getById(detailSummary.getPayEnterpriseId());
        if (null == byId) {
            return R.fail("数据错误");
        }
        String enterprisePayReceiptUrl = payEnterpriseReceiptService.findEnterprisePayReceiptUrl(byId.getId());
        detailSummary.setEnterprisePayReceiptUrl(enterprisePayReceiptUrl);
        map.put("EnterpriseSubcontractPortalVO", detailSummary);
        Query query = new Query();
        query.setSize(100);
        query.setCurrent(1);
        List<PayMakerListInvoiceVO> PayMakerListVOs = baseMapper.queryPayMakerListInvoice(byId.getId(), Condition.getPage(query.setDescs("t1.create_time")));
        map.put("payMakerListVOs", PayMakerListVOs);
        return R.data(map);
    }

    @Override
    public R<IPage<PayMakerListWebVO>> getPayMakerListByPayEnterprise(Long payEnterpriseId, IPage<PayMakerListWebVO> page) {
        return R.data(page.setRecords(baseMapper.getPayMakerListByPayEnterprise(payEnterpriseId, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> audit(Long payEnterpriseId, Long serviceProviderId, Long serviceProviderWorkerId, PayEnterpriseAuditState auditState, MakerInvoiceType makerInvoiceType) {

        PayEnterpriseEntity payEnterpriseEntity = getById(payEnterpriseId);
        if (payEnterpriseEntity == null) {
            return R.fail("总包支付清单不存在");
        }

        if (!(payEnterpriseEntity.getServiceProviderId().equals(serviceProviderId))) {
            return R.fail("总包支付清单不属于当前服务商");
        }

        if (!(PayEnterprisePayState.PAYED.equals(payEnterpriseEntity.getPayState()))) {
            return R.fail("总包支付清单的支付状态有误");
        }

        if (!(PayEnterpriseAuditState.SUBMITED.equals(payEnterpriseEntity.getAuditState()))) {
            return R.fail("总包支付清单的审核状态有误");
        }

        if (!(PayEnterpriseAuditState.APPROVED.equals(auditState)) && !(PayEnterpriseAuditState.REJECTED.equals(auditState))) {
            return R.fail("审核状态只能选择通过或者驳回");
        }

        if (PayEnterpriseAuditState.APPROVED.equals(auditState)) {
            if (makerInvoiceType == null) {
                return R.fail("请选择创客发票开票类别");
            }

            payEnterpriseEntity.setMakerInvoiceType(makerInvoiceType);
            payEnterpriseEntity.setEmployeeId(serviceProviderWorkerId);
        }

        payEnterpriseEntity.setAuditState(auditState);
        updateById(payEnterpriseEntity);

        return R.success("审核成功");
    }

    @Override
    public R<TransactionVO> queryEnterpriseTransaction(Long enterpriseId) {
        return R.data(baseMapper.queryEnterpriseTransaction(enterpriseId));
    }

    @Override
    public R<TransactionVO> queryServiceProviderTransaction(Long serviceProviderId) {
        return R.data(baseMapper.queryServiceProviderTransaction(serviceProviderId));
    }

    @Override
    public R<TransactionVO> queryAgentMainEnterpriseTransaction(Long agentMainId) {
        return R.data(baseMapper.queryAgentMainEnterpriseTransaction(agentMainId));
    }

    @Override
    public R<TransactionVO> queryAgentMainServiceProviderTransaction(Long agentMainId) {
        return R.data(baseMapper.queryAgentMainServiceProviderTransaction(agentMainId));
    }

    @Override
    public R<TransactionVO> queryPartnerEnterpriseTransaction(Long partnerId) {
        return R.data(baseMapper.queryPartnerEnterpriseTransaction(partnerId));
    }

    @Override
    public R<TransactionVO> queryRelBureauTransaction(Long relBureauId) {
        return R.data(baseMapper.queryRelBureauTransaction(relBureauId));
    }

    @Override
    public R<List<TradeVO>> queryTotalSubTrade(Long enterpriseId, Long serviceProviderId, Long relBureauId, TimeType timeType, Date beginDate, Date endDate) {

        if (TimeType.PERIOD.equals(timeType) && (beginDate == null || endDate == null)){
            return R.fail("请选择开始时间和结束时间");
        }

        return R.data(baseMapper.queryTotalSubTrade(enterpriseId, serviceProviderId, relBureauId, timeType.getValue(), beginDate, endDate));
    }

    @Override
    public R<IPage<TotalCrowdTradeListVO>> queryRelBureauTotalSublist(Long relBureauId, IPage<TotalCrowdTradeListVO> page) {
        return R.data(page.setRecords(baseMapper.queryRelBureauTotalSublist(relBureauId, page)));
    }

    @Override
    public R getServiceLumpSumInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, CompanyInvoiceState companyInvoiceState, IPage<InvoiceServiceLumpVO> page) {
        switch (companyInvoiceState) {
            case UNOPEN:
                return R.data(page.setRecords(baseMapper.getServiceLumpSumInvoice(serviceProviderId, enterpriseName, startTime, endTime, page)));
            case OPENED:
                return R.data(page.setRecords(baseMapper.getServiceOpenedLumpSumInvoice(serviceProviderId, enterpriseName, startTime, endTime, page)));
            default:
                return R.fail("参数错误");
        }
    }

    @Override
    public R queryTotalMergeInvoice(String payEnterpriseIds) {
        return R.data(baseMapper.queryTotalMergeInvoice(payEnterpriseIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R saveServiceLumpSumMergeInvoice(Long serviceProviderId, String payEnterpriseIds, String serviceProviderName, String companyInvoiceUrl, String expressSheetNo, String expressCompanyName, String invoiceDesc, String invoiceTypeNo, String invoiceSerialNo, String invoiceCategory, InvoiceMode invoiceMode, BigDecimal partInvoiceAmount) {
        if (InvoiceMode.PARTIALLYISSUED.equals(invoiceMode) && partInvoiceAmount.compareTo(BigDecimal.ZERO) == 0) {
            return R.fail("请输入部分开票的金额！！！");
        }
        String[] split = payEnterpriseIds.split(",");
        if (split.length <= 0) {
            return R.fail("参数有误");
        }
        BigDecimal invoiceTotalAmount = BigDecimal.ZERO;
        for (int i = 0; i < split.length; i++) {
            PayEnterpriseEntity byId = getById(Long.parseLong(split[i]));
            if (InvoiceState.OPENED.equals(byId.getCompanyInvoiceState())) {
                return R.fail("不能重复开票");
            }
            invoiceTotalAmount = byId.getPayToPlatformAmount().add(invoiceTotalAmount);
        }
        if (InvoiceMode.PARTIALLYISSUED.equals(invoiceMode) && partInvoiceAmount.compareTo(invoiceTotalAmount) > -1) {
            return R.fail("部分开票的金额不能大于等于价税合计额！！！");
        }
        PlatformInvoiceEntity platformInvoiceEntity = new PlatformInvoiceEntity();
        platformInvoiceEntity.setInvoicePrintDate(new Date());
        //价税合计
        platformInvoiceEntity.setInvoiceTotalAmount(invoiceTotalAmount);
        if (InvoiceMode.PARTIALLYISSUED.equals(invoiceMode)) {
            platformInvoiceEntity.setOpenedInvoiceTotalAmount(partInvoiceAmount);
        }

        platformInvoiceEntity.setInvoiceNumbers(1);
        if (null == serviceProviderName) {
            platformInvoiceEntity.setInvoicePrintPerson("地衣众包平台");
        } else {
            platformInvoiceEntity.setInvoicePrintPerson(serviceProviderName);
        }

        platformInvoiceEntity.setExpressSheetNo(expressSheetNo);
        platformInvoiceEntity.setExpressCompanyName(expressCompanyName);
        platformInvoiceEntity.setInvoiceDesc(invoiceDesc);
        platformInvoiceService.save(platformInvoiceEntity);


        for (int i = 0; i < split.length; i++) {
            PlatformInvoicePayListEntity platformInvoicePayListEntity = new PlatformInvoicePayListEntity();
            platformInvoicePayListEntity.setPayEnterpriseId(Long.parseLong(split[i]));
            platformInvoicePayListEntity.setInvoicePrintId(platformInvoiceEntity.getId());
            platformInvoicePayListService.save(platformInvoicePayListEntity);
        }

        PlatformInvoiceListEntity platformInvoiceListEntity = new PlatformInvoiceListEntity();
        platformInvoiceListEntity.setInvoicePrintId(platformInvoiceEntity.getId());
        //发票代码
        //platformInvoiceListEntity.setInvoiceTypeNo(invoiceTypeNo);
        //发票号码
        //platformInvoiceListEntity.setInvoiceSerialNo(invoiceSerialNo);
        platformInvoiceListEntity.setInvoiceCategory(invoiceCategory);
        platformInvoiceListEntity.setInvoiceDatetime(new Date());
        //价税合计
        platformInvoiceListEntity.setTotalAmount(invoiceTotalAmount);
        //金额合计
        platformInvoiceListEntity.setSalesAmount(invoiceTotalAmount);
        //税额合计
        platformInvoiceListEntity.setTaxAmount(invoiceTotalAmount);
        if (null == serviceProviderName) {
            platformInvoiceListEntity.setInvoicePerson("地衣众包平台");
        } else {
            platformInvoiceListEntity.setInvoicePerson(serviceProviderName);
        }
        //销售方名称
        //platformInvoiceListEntity.setSaleCompany(null == serviceProviderName ? "" : serviceProviderName);
        platformInvoiceListEntity.setCompanyInvoiceUrl(companyInvoiceUrl);
        platformInvoiceListService.save(platformInvoiceListEntity);
        //更新总包支付清单的总包开票状态
        for (int i = 0; i < split.length; i++) {
            PayEnterpriseEntity byId = getById(Long.parseLong(split[i]));
            if (InvoiceMode.PARTIALLYISSUED.equals(invoiceMode)) {
                byId.setCompanyInvoiceState(CompanyInvoiceState.PARTIALLYISSUED);
            } else {
                byId.setCompanyInvoiceState(CompanyInvoiceState.OPENED);
            }
            saveOrUpdate(byId);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createTotalApplyInvoice(Long serviceProviderId, String serviceProviderName, Long applicationId, String companyInvoiceUrl, String expressSheetNo, String expressCompanyName, String invoiceDesc, String invoiceTypeNo, String invoiceSerialNo, String invoiceCategory, InvoiceMode invoiceMode, BigDecimal partInvoiceAmount) {
        if (InvoiceMode.PARTIALLYISSUED.equals(invoiceMode) && partInvoiceAmount.compareTo(BigDecimal.ZERO) == 0) {
            return R.fail("请输入部分开票的金额！！！");
        }
        List<InvoiceApplicationPayListEntity> invoiceApplicationPayListEntityList = invoiceApplicationPayListService.getApplicationId(applicationId);
        if (invoiceApplicationPayListEntityList.size() <= 0) {
            return R.fail("参数有误");
        }
        BigDecimal invoiceTotalAmount = BigDecimal.ZERO;
        for (InvoiceApplicationPayListEntity invoiceApplicationPayListEntity : invoiceApplicationPayListEntityList) {
            PayEnterpriseEntity byId = getById(invoiceApplicationPayListEntity.getPayEnterpriseId());
            if (InvoiceState.OPENED.equals(byId.getCompanyInvoiceState())) {
                return R.fail("不能重复开票");
            }
            invoiceTotalAmount = byId.getPayToPlatformAmount().add(invoiceTotalAmount);
        }
        if (InvoiceMode.PARTIALLYISSUED.equals(invoiceMode) && partInvoiceAmount.compareTo(invoiceTotalAmount) > -1) {
            return R.fail("部分开票的金额不能大于等于价税合计额！！！");
        }
        PlatformInvoiceEntity platformInvoiceEntity = new PlatformInvoiceEntity();
        platformInvoiceEntity.setApplicationId(applicationId);
        platformInvoiceEntity.setInvoicePrintDate(new Date());
        //价税合计
        platformInvoiceEntity.setInvoiceTotalAmount(invoiceTotalAmount);
        if (InvoiceMode.PARTIALLYISSUED.equals(invoiceMode)) {
            platformInvoiceEntity.setOpenedInvoiceTotalAmount(partInvoiceAmount);
        }
        platformInvoiceEntity.setInvoiceNumbers(1);
        if (null == serviceProviderName) {
            platformInvoiceEntity.setInvoicePrintPerson("地衣众包平台");
        } else {
            platformInvoiceEntity.setInvoicePrintPerson(serviceProviderName);
        }

        platformInvoiceEntity.setExpressSheetNo(expressSheetNo);
        platformInvoiceEntity.setExpressCompanyName(expressCompanyName);
        platformInvoiceEntity.setInvoiceDesc(invoiceDesc);
        platformInvoiceService.save(platformInvoiceEntity);


        for (int i = 0; i < invoiceApplicationPayListEntityList.size(); i++) {
            PlatformInvoicePayListEntity platformInvoicePayListEntity = new PlatformInvoicePayListEntity();
            platformInvoicePayListEntity.setPayEnterpriseId(invoiceApplicationPayListEntityList.get(i).getPayEnterpriseId());
            platformInvoicePayListEntity.setInvoicePrintId(platformInvoiceEntity.getId());
            platformInvoicePayListService.save(platformInvoicePayListEntity);
        }

        PlatformInvoiceListEntity platformInvoiceListEntity = new PlatformInvoiceListEntity();
        platformInvoiceListEntity.setInvoicePrintId(platformInvoiceEntity.getId());
        //发票代码
        platformInvoiceListEntity.setInvoiceTypeNo(invoiceTypeNo);
        //发票号码
        platformInvoiceListEntity.setInvoiceSerialNo(invoiceSerialNo);
        platformInvoiceListEntity.setInvoiceCategory(invoiceCategory);
        platformInvoiceListEntity.setInvoiceDatetime(new Date());
        //价税合计
        platformInvoiceListEntity.setTotalAmount(invoiceTotalAmount);
        //金额合计
        platformInvoiceListEntity.setSalesAmount(invoiceTotalAmount);
        //税额合计
        platformInvoiceListEntity.setTaxAmount(invoiceTotalAmount);
        if (null == serviceProviderName) {
            platformInvoiceListEntity.setInvoicePerson("地衣众包平台");
        } else {
            platformInvoiceListEntity.setInvoicePerson(serviceProviderName);
        }
        //销售方名称
        platformInvoiceListEntity.setSaleCompany(null == serviceProviderName ? "" : serviceProviderName);
        platformInvoiceListEntity.setCompanyInvoiceUrl(companyInvoiceUrl);
        platformInvoiceListService.save(platformInvoiceListEntity);
        //更新总包支付清单的总包开票状态
        for (InvoiceApplicationPayListEntity invoiceApplicationPayListEntity : invoiceApplicationPayListEntityList) {
            PayEnterpriseEntity payEnterpriseEntity = getById(invoiceApplicationPayListEntity.getPayEnterpriseId());
            InvoiceApplicationEntity invoiceApplicationEntity = invoiceApplicationService.getById(invoiceApplicationPayListEntity.getApplicationId());
            if (InvoiceMode.PARTIALLYISSUED.equals(invoiceMode)) {
                payEnterpriseEntity.setCompanyInvoiceState(CompanyInvoiceState.PARTIALLYISSUED);
                invoiceApplicationEntity.setApplicationState(ApplicationState.PARTIALLYISSUED);
            } else {
                payEnterpriseEntity.setCompanyInvoiceState(CompanyInvoiceState.OPENED);
                invoiceApplicationEntity.setApplicationState(ApplicationState.ISSUEDINFULL);
            }
            saveOrUpdate(payEnterpriseEntity);
            invoiceApplicationService.saveOrUpdate(invoiceApplicationEntity);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateTotalInvoice(LumpInvoiceDTO lumpInvoiceDTO) {
        if (InvoiceMode.PARTIALLYISSUED.equals(lumpInvoiceDTO.getInvoiceMode()) && lumpInvoiceDTO.getPartInvoiceAmount().compareTo(BigDecimal.ZERO) == 0) {
            return R.fail("请输入部分开票的金额！！！");
        }

        PlatformInvoiceEntity platformInvoiceEntity = platformInvoiceService.getById(lumpInvoiceDTO.getInvoicePrintId());
        if (InvoiceMode.PARTIALLYISSUED.equals(lumpInvoiceDTO.getInvoiceMode())) {
            BigDecimal openedInvoiceTotalAmount = platformInvoiceEntity.getOpenedInvoiceTotalAmount();
            BigDecimal add = openedInvoiceTotalAmount.add(lumpInvoiceDTO.getPartInvoiceAmount());
            List<PlatformInvoicePayListEntity> platformInvoicePayListEntities = platformInvoicePayListService.findInvoicePrintId(lumpInvoiceDTO.getInvoicePrintId());
            if (add.compareTo(platformInvoiceEntity.getInvoiceTotalAmount()) == 0) {
                platformInvoiceEntity.setOpenedInvoiceTotalAmount(platformInvoiceEntity.getInvoiceTotalAmount());
                for (PlatformInvoicePayListEntity platformInvoicePayListEntity : platformInvoicePayListEntities) {
                    PayEnterpriseEntity payEnterpriseEntity = getById(platformInvoicePayListEntity.getPayEnterpriseId());
                    payEnterpriseEntity.setCompanyInvoiceState(CompanyInvoiceState.OPENED);
                    InvoiceApplicationEntity invoiceApplicationEntity = invoiceApplicationService.getById(platformInvoiceEntity.getApplicationId());
                    if (null != invoiceApplicationEntity) {
                        invoiceApplicationEntity.setApplicationState(ApplicationState.ISSUEDINFULL);
                        invoiceApplicationService.updateById(invoiceApplicationEntity);
                    }
                    saveOrUpdate(payEnterpriseEntity);
                }
            }
            if (add.compareTo(platformInvoiceEntity.getInvoiceTotalAmount()) > 0) {
                return R.fail("部分开票金额有误！！！");
            }
        } else {
            List<PlatformInvoicePayListEntity> platformInvoicePayListEntities = platformInvoicePayListService.findInvoicePrintId(lumpInvoiceDTO.getInvoicePrintId());
            for (PlatformInvoicePayListEntity platformInvoicePayListEntity : platformInvoicePayListEntities) {
                PayEnterpriseEntity payEnterpriseEntity = getById(platformInvoicePayListEntity.getPayEnterpriseId());
                payEnterpriseEntity.setCompanyInvoiceState(CompanyInvoiceState.OPENED);
                saveOrUpdate(payEnterpriseEntity);
            }
        }
        platformInvoiceEntity.setExpressCompanyName(lumpInvoiceDTO.getExpressCompanyName());
        platformInvoiceEntity.setExpressSheetNo(lumpInvoiceDTO.getExpressSheetNo());
        platformInvoiceService.updateById(platformInvoiceEntity);
        List<PlatformInvoiceListEntity> invoicePrintId = platformInvoiceListService.findInvoicePrintId(lumpInvoiceDTO.getInvoicePrintId());
        for (PlatformInvoiceListEntity platformInvoiceListEntity : invoicePrintId) {
            platformInvoiceListEntity.setInvoiceCategory(lumpInvoiceDTO.getInvoiceCategory());
            platformInvoiceListEntity.setCompanyInvoiceUrl(lumpInvoiceDTO.getCompanyInvoiceUrl());
            platformInvoiceListService.saveOrUpdate(platformInvoiceListEntity);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
        if (null == serviceProviderName) {
            makerTotalInvoiceEntity.setInvoicePerson("平台");
            makerTotalInvoiceEntity.setSaleCompany("平台");
        } else {
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
    @Transactional(rollbackFor = Exception.class)
    public R savePortalSignInvoice(Long serviceProviderId, Long payEnterpriseId, String payMakers, String serviceProviderName) {
        JSONArray payMakerArray = new JSONArray(payMakers);
        for (int i = 0; i < payMakerArray.length(); i++) {
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
            if (null == byId) {
                return R.fail("分包支付明细不存在");
            }
            String voicePerson = userClient.queryMakerById(byId.getMakerId()).getName();
            BigDecimal makerNeIncome = byId.getMakerNeIncome();
            BigDecimal salesAmount = makerNeIncome.divide(new BigDecimal("1").add(new BigDecimal("0.03")), 2);
            MakerInvoiceEntity makerInvoiceEntity = new MakerInvoiceEntity(byId.getId(), voiceTypeNo, voiceSerialNo, new Date(), voiceCategory, byId.getMakerNeIncome(), salesAmount, new BigDecimal(taxAmount), voicePerson, helpMakeOrganationName, helpMakeOrganationName, helpMakeCompany, helpMakeTaxNo, makerVoiceUrl, new Date());
            makerInvoiceService.save(makerInvoiceEntity);
            if (StringUtil.isNotBlank(makerTaxRecord)) {
                JSONArray makerTaxRecordArray = new JSONArray(payMakers);
                for (int j = 0; j < makerTaxRecordArray.length(); j++) {
                    String voiceTaxTypeNo = payMakerArray.getJSONObject(i).get("voiceTaxTypeNo").toString();
                    String voiceTaxSerialNo = payMakerArray.getJSONObject(i).get("voiceTaxSerialNo").toString();
                    String makerTaxUrl = payMakerArray.getJSONObject(i).get("makerTaxUrl").toString();
                    String makerTaxAmount = payMakerArray.getJSONObject(i).get("makerTaxAmount").toString();
                    MakerTaxRecordEntity makerTaxRecordEntity = new MakerTaxRecordEntity(byId.getId(), voiceTaxTypeNo, voiceTaxSerialNo, new BigDecimal(makerTaxAmount), byId.getMakerNeIncome(), salesAmount, new BigDecimal(taxAmount), voicePerson, helpMakeOrganationName, helpMakeOrganationName, makerTaxUrl, new Date(), new Date());
                    makerTaxRecordService.save(makerTaxRecordEntity);
                }
            }
        }
        PayEnterpriseEntity byId = getById(payEnterpriseId);
        byId.setSubcontractingInvoiceState(InvoiceState.OPENED);
        saveOrUpdate(byId);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R getServiceSummaryInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, IPage<InvoiceServiceSubVO> page) {
        return R.data(page.setRecords(baseMapper.getServiceSummaryInvoice(serviceProviderId, enterpriseName, startTime, endTime, page)));
    }

    @Override
    public R queryTotalInvoiceListEnterprise(Long enterpriseId, String serviceProviderName, IPage<TotalInvoiceListEnterVO> page) {
        return R.data(page.setRecords(baseMapper.queryTotalInvoiceListEnterprise(enterpriseId, serviceProviderName, page)));
    }

    @Override
    public R queryTotalInvoiceListEnterpriseApplyDetails(Long invoiceApplicationId) {
        return R.data(baseMapper.queryTotalInvoiceListEnterpriseApplyDetails(invoiceApplicationId));
    }

    @Override
    public R queryTotalInvoiceListEnterpriseInvoiceDetails(Long invoicePrintId) {
        Map map = new HashMap();
        List<EnterpriseInvoiceDetailVO> enterpriseInvoiceDetails = baseMapper.queryTotalInvoiceListEnterpriseInvoiceDetails(invoicePrintId);
        if (null != enterpriseInvoiceDetails && enterpriseInvoiceDetails.size() > 0) {
            String expressCompanyName = enterpriseInvoiceDetails.get(0).getExpressCompanyName();
            String expressSheetNo = enterpriseInvoiceDetails.get(0).getExpressSheetNo();
            KdniaoTrackQueryUtil kdniaoTrackQueryUtil = new KdniaoTrackQueryUtil();

            try {
                String result = kdniaoTrackQueryUtil.getOrderTracesByJson(expressCompanyName, expressSheetNo);
                Map<String, Object> maps = (Map) JSON.parse(result);
                Boolean success = (Boolean) maps.get("Success");
                if (success) {
                    map.put("orderTracesByJson", maps.get("Traces"));
                } else {
                    map.put("orderTracesByJson", "");
                }
            } catch (Exception e) {
                map.put("orderTracesByJson", "");
                log.info(e.getMessage());
            }
        }
        map.put("enterpriseInvoiceDetails", enterpriseInvoiceDetails);
        return R.data(map);
    }

    @Override
    public R queryRelationEnterpriseService(Long enterpriseId, String serviceProviderName, IPage<RelationEnterpriseServiceVO> page) {
        return R.data(page.setRecords(baseMapper.queryRelationEnterpriseService(enterpriseId, serviceProviderName, page)));
    }

    @Override
    public R queryEnterpriseServicePayList(Long enterpriseId, Long serviceProviderId, IPage<EnterpriseServicePayListVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseServicePayList(enterpriseId, serviceProviderId, page)));
    }

    @Override
    public R findServiceSubcontractSummary(Long serviceProviderId, String enterpriseName, CompanyInvoiceState companyInvoiceState, IPage<EnterpriseSubcontractInvoiceVO> page) {
        return R.data(page.setRecords(baseMapper.findServiceSubcontractSummary(serviceProviderId, enterpriseName, companyInvoiceState, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createSummaryAgencyInvoice(SummaryInvoiceDTO summaryInvoiceDTO) {
        String payEnterpriseIds = summaryInvoiceDTO.getPayEnterpriseIds();
        String[] split = payEnterpriseIds.split(",");
        if (split.length <= 0) {
            return R.fail("参数格式错误");
        }
        PayEnterpriseEntity byId = getById(Long.parseLong(split[0]));
        BigDecimal payToPlatformAmount = BigDecimal.ZERO;
        List<PayEnterpriseEntity> payEnterpriseEntities = new ArrayList<>();
        payEnterpriseEntities.add(byId);
        for (int i = 1; i < split.length; i++) {
            PayEnterpriseEntity payEnterpriseEntity = getById(Long.parseLong(split[i]));
            if (null == payEnterpriseEntity) {
                return R.fail("总包支付清单不存在");
            }
            if (!(byId.getMakerInvoiceType().equals(payEnterpriseEntity.getMakerInvoiceType()))) {
                return R.fail("请选择相同分包开票类别进行开票");
            }
            payEnterpriseEntities.add(payEnterpriseEntity);
        }

        for (int i = 0; i < split.length; i++) {
            MakerTotalInvoiceEntity makerTotalInvoiceEntity = makerTotalInvoiceService.findPayEnterpriseId(Long.parseLong(split[i]));
            if (null == makerTotalInvoiceEntity) {
                makerTotalInvoiceEntity = new MakerTotalInvoiceEntity();
            }
            makerTotalInvoiceEntity.setPayEnterpriseId(Long.parseLong(split[i]));
            makerTotalInvoiceEntity.setTaxAmount(payToPlatformAmount);
            makerTotalInvoiceEntity.setTotalAmount(new BigDecimal("0"));
            makerTotalInvoiceEntity.setInvoicePerson("");
            makerTotalInvoiceEntity.setSalesAmount(new BigDecimal("0"));
            makerTotalInvoiceEntity.setCompanyInvoiceUrl(summaryInvoiceDTO.getCompanyInvoiceUrl());
            makerTotalInvoiceEntity.setMakerTaxUrl(summaryInvoiceDTO.getMakerTaxUrl());
            makerTotalInvoiceEntity.setMakerTaxListUrl(summaryInvoiceDTO.getMakerTaxListUrl());
            makerTotalInvoiceEntity.setCompanyVoiceUploadDatetime(new Date());
            makerTotalInvoiceService.saveOrUpdate(makerTotalInvoiceEntity);
        }
        for (PayEnterpriseEntity payEnterpriseEntity : payEnterpriseEntities) {
            payEnterpriseEntity.setSubcontractingInvoiceState(InvoiceState.OPENED);
            saveOrUpdate(payEnterpriseEntity);
        }
        return R.success("汇总开票成功");
    }

    @Override
    public R findServiceDetailSummary(String payEnterpriseIds) {
        Map map = new HashMap();
        String[] split = payEnterpriseIds.split(",");
        if (split.length <= 0) {
            return R.fail("参数错误");
        }
        PayEnterpriseEntity payEnterpriseEntity = getById(Long.parseLong(split[0]));
        for (int i = 1; i < split.length; i++) {
            PayEnterpriseEntity byId = getById(Long.parseLong(split[i]));
            if (!payEnterpriseEntity.getMakerInvoiceType().equals(byId.getMakerInvoiceType())) {
                return R.fail("请选择相同的开票方式");
            }
        }
        List<InvoiceServiceDetailSummaryVO> invoiceServiceDetailSummaryVOS = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            InvoiceServiceDetailSummaryVO invoiceServiceDetailSummaryVO = baseMapper.findServiceDetailSummary(Long.parseLong(split[i]));
            String enterprisePayReceiptUrl = payEnterpriseReceiptService.findEnterprisePayReceiptUrl(Long.parseLong(split[i]));
            invoiceServiceDetailSummaryVO.setEnterprisePayReceiptUrl(enterprisePayReceiptUrl);
            invoiceServiceDetailSummaryVOS.add(invoiceServiceDetailSummaryVO);
        }
        map.put("invoiceServiceDetailSummaryVO", invoiceServiceDetailSummaryVOS);
        List<PayMakerListServiceProviderVO> payMakerLists = baseMapper.getPayMakerLists(payEnterpriseIds);
        map.put("payMakerListVOs", payMakerLists);
        return R.data(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createDoorSignInvoice(DoorSignInvoiceDTO doorSignInvoiceDTO) {
        String payEnterpriseIds = doorSignInvoiceDTO.getPayEnterpriseIds();
        String[] split = payEnterpriseIds.split(",");
        if (split.length <= 0) {
            return R.fail("参数错误");
        }
        BigDecimal payToPlatformAmount = BigDecimal.ZERO;
        List<PayEnterpriseEntity> payEnterpriseEntities = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            PayEnterpriseEntity payEnterpriseEntity = getById(Long.parseLong(split[i]));
            if (null == payEnterpriseEntity) {
                return R.fail("总包支付清单不存在");
            }
            payEnterpriseEntities.add(payEnterpriseEntity);
            payToPlatformAmount = payToPlatformAmount.add(payEnterpriseEntity.getPayToPlatformAmount());
        }
        Integer payMakerCount = payMakerService.getPayMakerCount(payEnterpriseIds);

        JSONArray doorSignInvoiceJsonArray = new JSONArray(doorSignInvoiceDTO.getDoorSignInvoiceJson());
        for (int i = 0; i < doorSignInvoiceJsonArray.length(); i++) {
            Long payMakerId = doorSignInvoiceJsonArray.getJSONObject(i).getLong("payMakerId");
            PayMakerEntity payMakerEntity = payMakerService.getById(payMakerId);
            String makerVoiceUrl = doorSignInvoiceJsonArray.getJSONObject(i).get("makerVoiceUrl").toString();
            String makerTaxUrl = doorSignInvoiceJsonArray.getJSONObject(i).get("makerTaxUrl").toString();
            MakerInvoiceEntity makerInvoiceEntity = makerInvoiceService.findPayMakerId(payMakerId);
            if (("").equals(makerVoiceUrl)) {
                return R.fail("请上传发票！！！");
            }
            if (null == makerInvoiceEntity) {
                makerInvoiceEntity = new MakerInvoiceEntity();
                makerInvoiceEntity.setPayMakerId(payMakerId);
            }
            makerInvoiceEntity.setMakerVoiceUrl(makerVoiceUrl);
            makerInvoiceEntity.setMakerVoiceUploadDateTime(new Date());
            makerInvoiceService.saveOrUpdate(makerInvoiceEntity);
            payMakerEntity.setMakerInvoiceState(InvoiceState.OPENED);
            if (StringUtils.isNotBlank(makerTaxUrl)) {
                MakerTaxRecordEntity makerTaxRecordEntity = makerTaxRecordService.findPayMakerId(payMakerId);
                if (null == makerTaxRecordEntity) {
                    makerTaxRecordEntity = new MakerTaxRecordEntity();
                    makerTaxRecordEntity.setPayMakerId(payMakerId);
                }
                makerTaxRecordEntity.setMakerTaxUrl(makerTaxUrl);
                makerTaxRecordEntity.setMakerTaxUploadDatetime(new Date());
                payMakerEntity.setMakerTaxState(InvoiceState.OPENED);
                makerTaxRecordService.saveOrUpdate(makerTaxRecordEntity);
            }
            payMakerService.saveOrUpdate(payMakerEntity);
        }
        if (payMakerCount == doorSignInvoiceJsonArray.length()) {
            for (PayEnterpriseEntity payEnterpriseEntity : payEnterpriseEntities) {
                payEnterpriseEntity.setSubcontractingInvoiceState(InvoiceState.OPENED);
                saveOrUpdate(payEnterpriseEntity);
            }
        }
        return R.success("门征单开成功");
    }

    @Override
    public R<PayEnterpriseDetailVO> queryPayEnterpriseDetail(Long payEnterpriseId) {
        return R.data(baseMapper.queryPayEnterpriseDetail(payEnterpriseId));
    }

    @Override
    public R<PayEnterpriseUpdateDetailVO> queryPayEnterpriseUpdateDetail(Long payEnterpriseId) {
        return R.data(baseMapper.queryPayEnterpriseUpdateDetail(payEnterpriseId));
    }

    @Override
    public R queryPayEnterpriseMakerList(Long payEnterpriseId) {
        return R.data(baseMapper.getPayMakerLists(payEnterpriseId + ""));
    }

    @Override
    public R<IPage<PayEnterpriseListSimpleVO>> queryPayEnterpriseListAgentMain(Long enterpriseId, Long serviceProviderId, PayEnterpriseListSimpleDTO payEnterpriseListSimpleDTO, IPage<PayEnterpriseListSimpleVO> page) {
        return R.data(page.setRecords(baseMapper.queryPayEnterpriseListAgentMain(enterpriseId, serviceProviderId, payEnterpriseListSimpleDTO, page)));
    }

    @Override
    public R<PayEnterpriseExpressVO> queryPayEnterpriseExpress(Long payEnterpriseId) {

        PayEnterpriseExpressVO payEnterpriseExpressVO = baseMapper.queryPayEnterpriseExpress(payEnterpriseId);
        //查询物流信息
        try {
            if (payEnterpriseExpressVO != null && StringUtil.isNotBlank(payEnterpriseExpressVO.getExpressCompanyName()) && StringUtil.isNotBlank(payEnterpriseExpressVO.getExpressSheetNo())) {
                KdniaoTrackQueryUtil kdniaoTrackQueryUtil = new KdniaoTrackQueryUtil();
                String result = kdniaoTrackQueryUtil.getOrderTracesByJson(payEnterpriseExpressVO.getExpressCompanyName(), payEnterpriseExpressVO.getExpressSheetNo());
                JSONObject jsonObject = JSON.parseObject(result);
                log.info(jsonObject.getString("Reason"));
                if (jsonObject.getBooleanValue("Success")) {
                    payEnterpriseExpressVO.setExpressDetail(jsonObject.getJSONArray("Traces"));
                }
            }
        } catch (Exception e) {
            log.error("查询物流异常", e);
        }

        return R.data(payEnterpriseExpressVO);
    }

}
