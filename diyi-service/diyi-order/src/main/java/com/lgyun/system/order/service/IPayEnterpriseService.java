package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CompanyInvoiceState;
import com.lgyun.common.enumeration.InvoiceMode;
import com.lgyun.common.enumeration.MakerInvoiceType;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.*;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.excel.PayEnterpriseExcel;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.dto.PayEnterpriseListSimpleDTO;
import com.lgyun.system.user.vo.TransactionVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service 接口
 *
 * @author tzq
 * @since 2020-07-17 20:01:13
 */
public interface IPayEnterpriseService extends BaseService<PayEnterpriseEntity> {

    /**
     * 根据创客查询所有商户
     *
     * @param makerId
     * @param page
     * @return
     */
    R<IPage<InvoiceEnterpriseVO>> getEnterpriseAll(Long makerId, IPage<InvoiceEnterpriseVO> page);

    /**
     * 根据创客和商户查询创客在商户下所开的票
     *
     * @param makerId
     * @param enterpriseId
     * @param page
     * @return
     */
    R<IPage<InvoiceEnterpriseVO>> getEnterpriseMakerIdAll(Long makerId, Long enterpriseId, IPage<InvoiceEnterpriseVO> page);

    /**
     * 根据创客,商户和分包查询票的详情
     *
     * @param makerId
     * @param enterpriseId
     * @param payMakerId
     * @return
     */
    R<InvoiceEnterpriseVO> getEnterpriseMakerIdDetail(Long makerId, Long enterpriseId, Long payMakerId);

    /**
     * 总包支付清单Excel文件读取
     *
     * @param chargeListUrl
     * @return
     */
    R<List<PayEnterpriseExcel>> readPayEnterpriseExcel(String chargeListUrl) throws Exception;

    /**
     * 上传或编辑总包支付清单
     *
     * @param payEnterpriseCreateOrUpdateDto
     * @param enterpriseId
     * @return
     */
    R<String> createOrUpdatePayEnterprise(PayEnterpriseCreateOrUpdateDTO payEnterpriseCreateOrUpdateDto, Long enterpriseId) throws Exception;

    /**
     * 提交支付清单
     *
     * @param payEnterpriseId
     * @param enterpriseId
     * @return
     */
    R<String> submit(Long payEnterpriseId, Long enterpriseId);

    /**
     * 根据条件查询所有总包支付清单
     *
     * @param enterpriseId
     * @param payEnterpriseDto
     * @param page
     * @return
     */
    R<IPage<PayEnterpriseListVO>> getPayEnterpriseList(Long enterpriseId, Long serviceProviderId, PayEnterpriseDTO payEnterpriseDto, IPage<PayEnterpriseListVO> page);

    /**
     * 取消申请
     *
     * @param applicationId
     * @return
     */
    R<String> cancelApply(Long applicationId);

    /**
     * 根据商户查询分包列表-汇总
     */
    R findEnterpriseSubcontractSummary(Long enterpriseId, String serviceProviderName, IPage<EnterpriseSubcontractInvoiceVO> page);

    /**
     * 根据商户查询分包列表-门征
     */
    R findEnterpriseSubcontractPortal(Long enterpriseId, String serviceProviderName, IPage<EnterpriseSubcontractPortalVO> page);

    /**
     * 查询详情接口-汇总
     */
    R findDetailSummary(Long makerTotalInvoiceId);

    /**
     * 查询详情接口-门征
     */
    R findDetailSubcontractPortal(Long makerInvoiceId);

    /**
     * 根据支付清单查询分包支付明细
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    R<IPage<PayMakerListWebVO>> getPayMakerListByPayEnterprise(Long payEnterpriseId, IPage<PayMakerListWebVO> page);

    /**
     * 总包审核
     *
     * @param payEnterpriseId
     * @param serviceProviderId
     * @param serviceProviderWorkerId
     * @param auditState
     * @param makerInvoiceType
     * @return
     */
    R<String> audit(Long payEnterpriseId, Long serviceProviderId, Long serviceProviderWorkerId, PayEnterpriseAuditState auditState, MakerInvoiceType makerInvoiceType);

    /**
     * 查询商户首页交易情况数据
     *
     * @param enterpriseId
     * @return
     */
    R<TransactionVO> queryEnterpriseTransaction(Long enterpriseId);

    /**
     * 查询服务商首页交易情况数据
     *
     * @param serviceProviderId
     * @return
     */
    R<TransactionVO> queryServiceProviderTransaction(Long serviceProviderId);

    /**
     * 查询渠道商-商户交易数据
     *
     * @param agentMainId
     * @return
     */
    R<TransactionVO> queryAgentMainEnterpriseTransaction(Long agentMainId);

    /**
     * 查询渠道商-服务商交易数据
     *
     * @param agentMainId
     * @return
     */
    R<TransactionVO> queryAgentMainServiceProviderTransaction(Long agentMainId);

    /**
     * 查询合伙人-服务商交易数据
     *
     * @param partnerId
     * @return
     */
    R<TransactionVO> queryPartnerEnterpriseTransaction(Long partnerId);

    /**
     * 查询相关局-服务商交易情况数据
     *
     * @param relBureauId
     * @return
     */
    R<TransactionVO> queryRelBureauServiceProviderTransaction(Long relBureauId);

    /**
     * 查询商户总包+分包全年流水
     *
     * @param enterpriseId
     * @return
     */
    R<YearTradeVO> queryTotalSubYearTradeByEnterprise(Long enterpriseId);

    /**
     * 查询服务商总包+分包全年流水
     *
     * @param serviceProviderId
     * @param relBureauId
     * @return
     */
    R<YearTradeVO> queryTotalSubYearTradeByServiceProvider(Long serviceProviderId, Long relBureauId);

    /**
     * 查询商户总包+分包本月流水
     *
     * @param enterpriseId
     * @return
     */
    R<MonthTradeVO> queryTotalSubMonthTradeByEnterprise(Long enterpriseId);

    /**
     * 查询服务商总包+分包本月流水
     *
     * @param serviceProviderId
     * @param relBureauId
     * @return
     */
    R<MonthTradeVO> queryTotalSubMonthTradeByServiceProvider(Long serviceProviderId, Long relBureauId);

    /**
     * 查询商户总包+分包本周流水
     *
     * @param enterpriseId
     * @return
     */
    R<WeekTradeVO> queryTotalSubWeekTradeByEnterprise(Long enterpriseId);

    /**
     * 查询服务商总包+分包本周流水
     *
     * @param serviceProviderId
     * @param relBureauId
     * @return
     */
    R<WeekTradeVO> queryTotalSubWeekTradeByServiceProvider(Long serviceProviderId, Long relBureauId);

    /**
     * 查询商户总包+分包今日流水
     *
     * @param enterpriseId
     * @return
     */
    R<DayTradeVO> queryTotalSubDayTradeByEnterprise(Long enterpriseId);

    /**
     * 查询服务商总包+分包今日流水
     *
     * @param serviceProviderId
     * @param relBureauId
     * @return
     */
    R<DayTradeVO> queryTotalSubDayTradeByServiceProvider(Long serviceProviderId, Long relBureauId);

    /**
     * 服务商查询总包发票列表
     *
     * @param serviceProviderId
     * @param enterpriseName
     * @param startTime
     * @param endTime
     * @param companyInvoiceState
     * @param page
     * @return
     */
    R getServiceLumpSumInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, CompanyInvoiceState companyInvoiceState, IPage<InvoiceServiceLumpVO> page);

    /**
     * 服务商总包合并开票
     *
     * @param payEnterpriseIds
     * @return
     */
    R queryTotalMergeInvoice(String payEnterpriseIds);

    /**
     * 服务商合并开票
     */
    R saveServiceLumpSumMergeInvoice(Long serviceProviderId, String payEnterpriseIds, String serviceProviderName, String companyInvoiceUrl, String expressSheetNo, String expressCompanyName, String invoiceDesc, String invoiceTypeNo, String invoiceSerialNo, String invoiceCategory, InvoiceMode invoiceMode, BigDecimal partInvoiceAmount);


    /**
     * 服务商总包根据申请开票
     *
     * @return
     */
    R createTotalApplyInvoice(Long serviceProviderId, String serviceProviderName, Long applicationId, String companyInvoiceUrl, String expressSheetNo, String expressCompanyName, String invoiceDesc, String invoiceTypeNo, String invoiceSerialNo, String invoiceCategory, InvoiceMode invoiceMode, BigDecimal partInvoiceAmount);

    /**
     * 服务商根据总包开票修改总包发票
     *
     * @return
     */
    R updateTotalInvoice(LumpInvoiceDTO lumpInvoiceDTO);

    /**
     * 服务商汇总代开开票
     */
    R saveSummaryInvoice(Long serviceProviderId, Long payEnterpriseId, String serviceProviderName, String invoiceTypeNo, String invoiceSerialNo, String invoiceCategory, String companyInvoiceUrl, String makerTaxUrl, String makerTaxListUrl);

    /**
     * 服务商门征单开发票开票
     */
    R savePortalSignInvoice(Long serviceProviderId, Long payEnterpriseId, String payMakers, String serviceProviderName);

    /**
     * 服务商查询已开票的汇总代开发票
     */
    R getServiceSummaryInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, IPage<InvoiceServiceSubVO> page);

    /**
     * 商户端根据商户查询总包
     */
    R queryTotalInvoiceListEnterprise(Long enterpriseId, String serviceProviderName, IPage<TotalInvoiceListEnterVO> page);

    /**
     * 商户端根据商户查询总包申请的详情总包支付清单
     */
    R queryTotalInvoiceListEnterpriseApplyDetails(Long invoiceApplicationId);

    /**
     * 商户端根据商户查询总包开票的详情总包支付清单
     */
    R queryTotalInvoiceListEnterpriseInvoiceDetails(Long invoicePrintId);

    /**
     * 查询和商户关联的服务商
     */
    R queryRelationEnterpriseService(Long enterpriseId, String serviceProviderName, IPage<RelationEnterpriseServiceVO> page);

    /**
     * 根据商户和服务商查询支付清单
     */
    R queryEnterpriseServicePayList(Long enterpriseId, Long serviceProviderId, IPage<EnterpriseServicePayListVO> page);


    /**
     * 根据服务商查询汇总代开分包列表
     */
    R findServiceSubcontractSummary(Long serviceProviderId, String enterpriseName, CompanyInvoiceState companyInvoiceState, IPage<EnterpriseSubcontractInvoiceVO> page);

    /**
     * 服务商汇总代开发票
     */
    R createSummaryAgencyInvoice(SummaryInvoiceDTO summaryInvoiceDTO);

    /**
     * 服务商根据总包支付清单查询分包详情
     */
    R findServiceDetailSummary(String payEnterpriseIds);


    /**
     * 服务商门征发票
     */
    R createDoorSignInvoice(DoorSignInvoiceDTO doorSignInvoiceDTO);


    /**
     * 查询总包支付清单详情
     *
     * @param payEnterpriseId
     * @return
     */
    R<PayEnterpriseDetailVO> queryPayEnterpriseDetail(Long payEnterpriseId);

    /**
     * 查询编辑总包支付清单详情
     *
     * @param payEnterpriseId
     * @return
     */
    R<PayEnterpriseUpdateDetailVO> queryPayEnterpriseUpdateDetail(Long payEnterpriseId);

    /**
     * 根据支付清单查询支付明细
     *
     * @param payEnterpriseId
     * @return
     */
    R queryPayEnterpriseMakerList(Long payEnterpriseId);

    /**
     * 查询商户总包+分包
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param payEnterpriseListSimpleDTO
     * @param page
     * @return
     */
    R<IPage<PayEnterpriseListSimpleVO>> queryPayEnterpriseListAgentMain(Long enterpriseId, Long serviceProviderId, PayEnterpriseListSimpleDTO payEnterpriseListSimpleDTO, IPage<PayEnterpriseListSimpleVO> page);

    /**
     * 查询总包支付清单物流信息
     *
     * @param payEnterpriseId
     * @return
     */
    R<PayEnterpriseExpressVO> queryPayEnterpriseExpress(Long payEnterpriseId);
}

