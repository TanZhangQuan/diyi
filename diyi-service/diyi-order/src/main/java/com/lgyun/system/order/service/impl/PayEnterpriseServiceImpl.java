package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.KdniaoTrackQueryUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.PayEnterpriseMakerListDto;
import com.lgyun.system.order.dto.PayEnterpriseUploadDto;
import com.lgyun.system.order.dto.SelfHelpInvoicePayDto;
import com.lgyun.system.order.entity.InvoiceApplicationEntity;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.entity.PayEnterpriseReceiptEntity;
import com.lgyun.system.order.entity.WorksheetEntity;
import com.lgyun.system.order.mapper.PayEnterpriseMapper;
import com.lgyun.system.order.service.*;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.entity.EnterpriseProviderEntity;
import com.lgyun.system.user.feign.IUserClient;
import com.lgyun.system.user.vo.TransactionVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ISelfHelpInvoiceService selfHelpInvoiceService;
    private IInvoiceApplicationService invoiceApplicationService;
    private IWorksheetMakerService worksheetMakerService;

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
        if (null != enterpriseMakerIdDetail.getMakerNum() && enterpriseMakerIdDetail.getMakerNum() > 1) {
            return R.fail("抱歉，由于此发票人数过多，你没有权限观看");
        }
        return R.data(enterpriseMakerIdDetail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> upload(PayEnterpriseUploadDto payEnterpriseUploadDto, Long enterpriseId) {

        //判断服务商和商户是否关联
        EnterpriseProviderEntity enterpriseProviderEntity = userClient.findByEnterpriseIdServiceProviderId(enterpriseId, payEnterpriseUploadDto.getServiceProviderId());
        if (enterpriseProviderEntity == null) {
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
        String[] split = payEnterpriseUploadDto.getPayReceiptUrl().split(",");
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
    public R<IPage<PayEnterpriseMakersListVO>> getPayEnterprises(Long enterpriseId, Long serviceProviderId, PayEnterpriseMakerListDto payEnterpriseMakerListDto, IPage<PayEnterpriseMakersListVO> page) {

        if (payEnterpriseMakerListDto.getBeginDate() != null && payEnterpriseMakerListDto.getEndDate() != null) {
            if (payEnterpriseMakerListDto.getBeginDate().after(payEnterpriseMakerListDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getPayEnterprises(enterpriseId, serviceProviderId, payEnterpriseMakerListDto, page)));
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
    public R getServiceProviderByEnterpriseId(Query query, Long enterpriseId, String serviceProviderName) {
        return userClient.getServiceProviderByEnterpriseId(query.getCurrent(), query.getSize(), enterpriseId, serviceProviderName);
    }

    @Override
    public R<IPage<SelfHelpInvoicePayVO>> getSelfHelfInvoiceByEnterpriseId(Long enterpriseId, SelfHelpInvoicePayDto selfHelpInvoicePayDto, IPage<SelfHelpInvoicePayVO> page) {
        return selfHelpInvoiceService.getSelfHelfInvoiceByEnterpriseId(enterpriseId, selfHelpInvoicePayDto, page);
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
    public R withdraw(Long applicationId) {
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
    public R findDetailSummary(Long makerTotalInvoiceId) {
        Map map = new HashMap();
        EnterpriseSubcontractInvoiceVO detailSummary = baseMapper.findDetailSummary(makerTotalInvoiceId);
        map.put("enterpriseSubcontractInvoiceVO", detailSummary);
        PayEnterpriseEntity byId = getById(detailSummary.getPayEnterpriseId());
        if (null == byId) {
            R.fail("数据错误");
        }
        Long worksheetId = byId.getWorksheetId();
        if (null != worksheetId) {
            List<WorksheetMakerDetailsVO> worksheetMakerDetails = worksheetMakerService.getWorksheetMakerDetails(worksheetId);
            map.put("worksheetMakerDetails", worksheetMakerDetails);
        } else {
            map.put("makers", "");
        }
        return R.data(map);
    }

    @Override
    public R findDetailSubcontractPortal(Long makerInvoiceId) {
        Map map = new HashMap();
        EnterpriseSubcontractPortalVO detailSummary = baseMapper.findDetailSubcontractPortal(makerInvoiceId);
        map.put("EnterpriseSubcontractPortalVO", detailSummary);
        PayEnterpriseEntity byId = getById(detailSummary.getPayEnterpriseId());
        if (null == byId) {
            R.fail("数据错误");
        }
        Long worksheetId = byId.getWorksheetId();
        if (null != worksheetId) {
            List<WorksheetMakerDetailsVO> worksheetMakerDetails = worksheetMakerService.getWorksheetMakerDetails(worksheetId);
            map.put("worksheetMakerDetails", worksheetMakerDetails);
        } else {
            map.put("makers", "");
        }
        return R.data(map);
    }

    @Override
    public R<IPage<PayMakerListVO>> getPayMakerListByPayEnterpriseId(Long payEnterpriseId, IPage<PayMakerListVO> page) {
        return R.data(page.setRecords(baseMapper.getPayMakerListByPayEnterpriseId(payEnterpriseId, page)));
    }

    @Override
    public R<String> audit(Long payEnterpriseId, Long serviceProviderId, PayEnterpriseAuditState auditState, MakerInvoiceType makerInvoiceType) {

        PayEnterpriseEntity payEnterpriseEntity = getById(payEnterpriseId);
        if (payEnterpriseEntity == null) {
            return R.fail("支付清单不存在");
        }

        if (!(payEnterpriseEntity.getServiceProviderId().equals(serviceProviderId))) {
            return R.fail("支付清单不属于当前服务商");
        }

        if (!(EnterprisePayState.PAYED.equals(payEnterpriseEntity.getPayState()))) {
            return R.fail("支付清单支付状态有误");
        }

        if (!(PayEnterpriseAuditState.SUBMITED.equals(payEnterpriseEntity.getAuditState()))) {
            return R.fail("支付清单审核状态有误");
        }

        if (!(PayEnterpriseAuditState.APPROVED.equals(auditState)) && !(PayEnterpriseAuditState.REJECTED.equals(auditState))) {
            return R.fail("审核状态有误");
        }

        payEnterpriseEntity.setAuditState(auditState);
        if (PayEnterpriseAuditState.APPROVED.equals(auditState)) {
            if (makerInvoiceType == null) {
                return R.fail("请选择创客发票开票类别");
            }

            payEnterpriseEntity.setMakerInvoiceType(makerInvoiceType);
            payEnterpriseEntity.setPayState(EnterprisePayState.CONFIRMPAY);
        }
        updateById(payEnterpriseEntity);

        return R.success("审核成功");
    }

    @Override
    public R<IPage<PayEnterpriseMakersListVO>> getPayEnterprisesByEnterprisesServiceProvider(Long enterpriseId, Long serviceProviderId, PayEnterpriseMakerListDto payEnterpriseMakerListDto, IPage<PayEnterpriseMakersListVO> page) {

        if (payEnterpriseMakerListDto.getBeginDate() != null && payEnterpriseMakerListDto.getEndDate() != null) {
            if (payEnterpriseMakerListDto.getBeginDate().after(payEnterpriseMakerListDto.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.getPayEnterprisesByEnterprisesServiceProvider(enterpriseId, serviceProviderId, payEnterpriseMakerListDto, page)));
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

}
