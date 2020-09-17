package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.MakerInvoiceType;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
import com.lgyun.common.enumeration.WorkSheetType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.PayEnterpriseDto;
import com.lgyun.system.order.dto.PayEnterpriseUploadDto;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.vo.TransactionVO;

/**
 * Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
public interface IPayEnterpriseService extends BaseService<PayEnterpriseEntity> {

    /**
     * 根据创客id查询所有商户
     *
     * @param makerId
     * @param page
     * @return
     */
    R<IPage<InvoiceEnterpriseVO>> getEnterpriseAll(Long makerId, IPage<InvoiceEnterpriseVO> page);

    /**
     * 根据创客id和商户id查询创客在商户下所开的票
     *
     * @param makerId
     * @param enterpriseId
     * @param page
     * @return
     */
    R<IPage<InvoiceEnterpriseVO>> getEnterpriseMakerIdAll(Long makerId, Long enterpriseId, IPage<InvoiceEnterpriseVO> page);

    /**
     * 根据创客id,商户id和创客支付id查询票的详情
     *
     * @param makerId
     * @param enterpriseId
     * @param payMakerId
     * @return
     */
    R<InvoiceEnterpriseVO> getEnterpriseMakerIdDetail(Long makerId, Long enterpriseId, Long payMakerId);

    /**
     * 当前商户上传总包支付清单
     *
     * @param payEnterpriseUploadDto
     * @param enterpriseId
     * @return
     */
    R<String> upload(PayEnterpriseUploadDto payEnterpriseUploadDto, Long enterpriseId) throws Exception;

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
    R<IPage<PayEnterpriseMakersListVO>> getPayEnterpriseList(Long enterpriseId, Long serviceProviderId, PayEnterpriseDto payEnterpriseDto, IPage<PayEnterpriseMakersListVO> page);

    /**
     * 根据支付清单ID查询支付清单关联工单的创客
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    R<IPage<PayEnterpriseMakerListVO>> getMakers(Long payEnterpriseId, IPage<PayEnterpriseMakerListVO> page);

    /**
     * 根据商户查询总包发票
     *
     * @param enterpriseId
     * @param page
     * @return
     */
    R<IPage<EnterpriseLumpSumInvoiceVO>> findEnterpriseLumpSumInvoice(String invoiceSerialNo, String serviceProviderName, String startTime, String endTime, Long enterpriseId, IPage<EnterpriseLumpSumInvoiceVO> page);

    /**
     * 取消申请
     */
    R withdraw(Long applicationId);

    /**
     * 查看总包发票详情
     */
    R findPayEnterpriseDetails(Long payEnterpriseId);

    /**
     * 根据商户查询支付清单
     */
    R findEnterprisePaymentList(Long enterpriseId, String serviceProviderName, IPage<EnterprisePaymentListVO> page);

    /**
     * 查询当前商户所有已完毕的总包+分包类型的工单
     *
     * @param query
     * @param enterpriseId
     * @param subpackage
     * @param worksheetNo
     * @param worksheetName
     * @return
     */
    R<IPage<WorksheetByEnterpriseVO>> getWorksheetByEnterpriseId(Query query, Long enterpriseId, WorkSheetType subpackage, String worksheetNo, String worksheetName);

    /**
     * 查询当前商户关联服务商
     *
     * @param query
     * @param enterpriseId
     * @param serviceProviderName
     * @return
     */
    R getServiceProviderByEnterpriseId(Query query, Long enterpriseId, String serviceProviderName);

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
     * 根据支付清单ID查询创客支付明细
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    R<IPage<PayMakerListVO>> getPayMakerListByPayEnterprise(Long payEnterpriseId, IPage<PayMakerListVO> page);

    /**
     * 总包审核
     *
     * @param payEnterpriseId
     * @param serviceProviderId
     * @param auditState
     * @param makerInvoiceType
     * @return
     */
    R<String> audit(Long payEnterpriseId, Long serviceProviderId, PayEnterpriseAuditState auditState, MakerInvoiceType makerInvoiceType);

    /**
     * 根据当前服务商，商户ID查询总包支付清单
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param payEnterpriseDto
     * @param page
     * @return
     */
    R<IPage<PayEnterpriseMakersListVO>> getPayEnterprisesByEnterprisesServiceProvider(Long enterpriseId, Long serviceProviderId, PayEnterpriseDto payEnterpriseDto, IPage<PayEnterpriseMakersListVO> page);

    /**
     * 查询当前商户首页交易情况数据
     *
     * @param enterpriseId
     * @return
     */
    R<TransactionVO> transactionByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商首页交易情况数据
     *
     * @param serviceProviderId
     * @return
     */
    R<TransactionVO> transactionByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户总包+分包全年流水
     *
     * @param enterpriseId
     * @return
     */
    R<YearTradeVO> queryTotalSubYearTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商总包+分包全年流水
     *
     * @param serviceProviderId
     * @return
     */
    R<YearTradeVO> queryTotalSubYearTradeByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户总包+分包本月流水
     *
     * @param enterpriseId
     * @return
     */
    R<MonthTradeVO> queryTotalSubMonthTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商总包+分包本月流水
     *
     * @param serviceProviderId
     * @return
     */
    R<MonthTradeVO> queryTotalSubMonthTradeByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户总包+分包本周流水
     *
     * @param enterpriseId
     * @return
     */
    R<WeekTradeVO> queryTotalSubWeekTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商总包+分包本周流水
     *
     * @param serviceProviderId
     * @return
     */
    R<WeekTradeVO> queryTotalSubWeekTradeByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户总包+分包今日流水
     *
     * @param enterpriseId
     * @return
     */
    R<DayTradeVO> queryTotalSubDayTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商总包+分包今日流水
     *
     * @param serviceProviderId
     * @return
     */
    R<DayTradeVO> queryTotalSubDayTradeByServiceProvider(Long serviceProviderId);


    /**
     *服务商查询总包发票
     */
    R getServiceLumpSumInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, InvoiceState companyInvoiceState,IPage<InvoiceServiceLumpVO> page);


    /**
     * 服务商查询总包发票详情
     * @param payEnterpriseId
     * @return
     */
    R getServiceLumpSumInvoiceDetails(Long payEnterpriseId);

    /**
     * 服务商总包开票
     * @param serviceProviderId
     * @param payEnterpriseId
     * @param applicationId
     * @param companyInvoiceUrl
     * @param expressSheetNo
     * @param expressCompanyName
     * @return
     */
    R saveServiceLumpSumInvoice(Long serviceProviderId,Long payEnterpriseId,String serviceProviderName,Long applicationId,String companyInvoiceUrl,String expressSheetNo,String expressCompanyName,String invoiceDesc);

    /**
     * 服务商查询未开票分包发票
     * @param serviceProviderId
     * @param enterpriseName
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    R getSubcontractInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime,IPage<InvoiceServiceSubVO> page);


    /**
     * 服务商查看未开票分包发票详情
     * @param payEnterpriseId
     * @return
     */
    R getSubcontractInvoiceDetails(Long payEnterpriseId);

    /**
     * 服务商汇总代开开票
     */
    R saveSummaryInvoice(Long serviceProviderId,Long payEnterpriseId,String serviceProviderName,String invoiceTypeNo,String invoiceSerialNo,String invoiceCategory,String companyInvoiceUrl,String makerTaxUrl,String makerTaxListUrl);

    /**
     * 服务商申请门征单开发票
     */
    R applyPortalSignInvoice(Long payEnterpriseId);

    /**
     * 服务商门征单开发票开票
     */
    R savePortalSignInvoice(Long serviceProviderId,Long payEnterpriseId,String payMakers,String serviceProviderName);

    /**
     * 服务商查询已开票的汇总代开发票
     */
    R getServiceSummaryInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime,IPage<InvoiceServiceSubVO> page);

    /**
     * 服务商查询已开票的汇总代开发票详情
     */
    R getSummaryInvoiceDetails(Long payEnterpriseId);

    /**
     * 服务商查询已门征单开的发票
     */
    R getServicePortalSignInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime,IPage<InvoiceServiceSubVO> page);

    /**
     * 服务商查询已门征单开的发票详情
     */
    R getServicePortalSignInvoiceDetails(Long payEnterpriseId);

}

