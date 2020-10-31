package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.system.order.dto.PayEnterpriseDTO;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.order.vo.TransactionByBureauServiceProviderInfoVO;
import com.lgyun.system.user.vo.TransactionVO;
import com.lgyun.system.user.vo.AdminAgentMainServiceProviderListVO;
import com.lgyun.system.user.vo.AgentMainTransactionVO;
import com.lgyun.system.user.vo.PartnerServiceProviderListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
@Mapper
public interface PayEnterpriseMapper extends BaseMapper<PayEnterpriseEntity> {

    /**
     * 根据创客id查询所有商户
     *
     * @param makerId
     * @param page
     * @return
     */
    List<InvoiceEnterpriseVO> getEnterpriseAll(Long makerId, IPage<InvoiceEnterpriseVO> page);

    /**
     * 根据创客id查询所有商户
     *
     * @param makerId
     * @param enterpriseId
     * @param page
     * @return
     */
    List<InvoiceEnterpriseVO> getEnterpriseMakerIdAll(Long makerId, Long enterpriseId, IPage<InvoiceEnterpriseVO> page);

    /**
     * 根据创客id查询所有商户
     *
     * @param makerId
     * @param enterpriseId
     * @param payMakerId
     * @return
     */
    InvoiceEnterpriseVO getEnterpriseMakerIdDetail(Long makerId, Long enterpriseId, Long payMakerId);

    /**
     * 根据条件查询所有商户支付清单
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param payEnterpriseDto
     * @param page
     * @return
     */
    List<PayEnterpriseListVO> getPayEnterpriseList(Long enterpriseId, Long serviceProviderId, PayEnterpriseDTO payEnterpriseDto, IPage<PayEnterpriseListVO> page);

    /**
     * 根据支付清单ID查询支付清单关联工单的创客
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    List<PayEnterpriseMakerListVO> getMakers(Long payEnterpriseId, IPage<PayEnterpriseMakerListVO> page);

    /**
     * 根据商户查询总包发票
     *
     * @param enterpriseId
     */
    List<EnterpriseLumpSumInvoiceVO> findEnterpriseLumpSumInvoice(String invoiceSerialNo, String serviceProviderName, String startTime, String endTime, Long enterpriseId, IPage<EnterpriseLumpSumInvoiceVO> page);

    /**
     * 查看总包发票详情
     *
     * @param payEnterpriseId
     * @return
     */
    EnterpriseLumpSumInvoiceVO findPayEnterpriseDetails(Long payEnterpriseId);

    /**
     * 根据商户查询支付清单
     */
    List<EnterprisePaymentListVO> findEnterprisePaymentList(Long enterpriseId, String serviceProviderName, IPage<EnterprisePaymentListVO> page);

    /**
     * 根据商户查询分包列表-汇总
     */
    List<EnterpriseSubcontractInvoiceVO> findEnterpriseSubcontractSummary(Long enterpriseId, String serviceProviderName, IPage<EnterpriseSubcontractInvoiceVO> page);

    /**
     * 根据商户查询分包列表-门征
     */
    List<EnterpriseSubcontractPortalVO> findEnterpriseSubcontractPortal(Long enterpriseId, String serviceProviderName, IPage<EnterpriseSubcontractPortalVO> page);

    /**
     * 分包列表详情-汇总
     */
    EnterpriseSubcontractInvoiceVO findDetailSummary(Long makerTotalInvoiceId);

    /**
     * 分包列表详情-门征
     */
    EnterpriseSubcontractPortalVO findDetailSubcontractPortal(Long makerInvoiceId);

    /**
     * 根据支付清单ID查询创客支付明细
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    List<PayMakerListInvoiceVO> queryPayMakerListInvoice(Long payEnterpriseId, IPage<PayMakerListVO> page);

    /**
     * 根据支付清单ID查询创客支付明细
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    List<PayMakerListVO> getPayMakerListByPayEnterprise(Long payEnterpriseId, IPage<PayMakerListVO> page);

    /**
     * 查询当前商户首页交易情况数据
     *
     * @param enterpriseId
     * @return
     */
    TransactionVO transactionByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商首页交易情况数据
     *
     * @param serviceProviderId
     * @return
     */
    TransactionVO transactionByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户总包+分包全年流水
     *
     * @param enterpriseId
     * @return
     */
    YearTradeVO queryTotalSubYearTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商总包+分包全年流水
     *
     * @param serviceProviderId
     * @return
     */
    YearTradeVO queryTotalSubYearTradeByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户总包+分包本月流水
     *
     * @param enterpriseId
     * @return
     */
    MonthTradeVO queryTotalSubMonthTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商总包+分包本月流水
     *
     * @param serviceProviderId
     * @return
     */
    MonthTradeVO queryTotalSubMonthTradeByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户总包+分包本周流水
     *
     * @param enterpriseId
     * @return
     */
    WeekTradeVO queryTotalSubWeekTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商总包+分包本周流水
     *
     * @param serviceProviderId
     * @return
     */
    WeekTradeVO queryTotalSubWeekTradeByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户总包+分包今日流水
     *
     * @param enterpriseId
     * @return
     */
    DayTradeVO queryTotalSubDayTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商总包+分包今日流水
     *
     * @param serviceProviderId
     * @return
     */
    DayTradeVO queryTotalSubDayTradeByServiceProvider(Long serviceProviderId);

    /**
     * 服务商查询未开总包发票
     *
     * @param serviceProviderId
     * @param enterpriseName
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    List<InvoiceServiceLumpVO> getServiceLumpSumInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, IPage<InvoiceServiceLumpVO> page);


    /**
     * 服务商查询已开总包发票
     */
    List<InvoiceServiceLumpVO> getServiceOpenedLumpSumInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, IPage<InvoiceServiceLumpVO> page);

    /**
     * 服务商查询总包发票详情
     *
     * @param payEnterpriseId
     * @return
     */
    InvoiceServiceLumpDetailsVO getServiceLumpSumInvoiceDetails(Long payEnterpriseId);


    /**
     * 服务商查询未开票分包发票
     *
     * @param serviceProviderId
     * @param enterpriseName
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    List<InvoiceServiceSubVO> getSubcontractInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, IPage<InvoiceServiceSubVO> page);

    /**
     * 服务商查看分包发票详情
     *
     * @param payEnterpriseId
     * @return
     */
    InvoiceServiceSubDetailsVO getSubcontractInvoiceDetails(Long payEnterpriseId);


    /**
     * 服务商查询已开票的汇总代开发票
     */
    List<InvoiceServiceSubVO> getServiceSummaryInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, IPage<InvoiceServiceSubVO> page);


    /**
     * 服务商查询已开票的门征代开发票
     */
    List<InvoiceServiceSubVO> getServicePortalSignInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, IPage<InvoiceServiceSubVO> page);

    /**
     * 查询当前税务局管理所有匹配的服务商交易情况数据
     *
     * @param bureauId
     * @return
     */
    TransactionVO transactionByBureauServiceProvider(@Param("bureauId") Long bureauId);

    /**
     * 相关局查询匹配的服务商基本信息及交易金额
     *
     * @param bureauId
     * @param page
     * @return
     */
    List<TransactionByBureauServiceProviderInfoVO> transactionByBureauServiceProviderInfo(@Param("bureauId") Long bureauId, IPage<TransactionByBureauServiceProviderInfoVO> page);


    /**
     * 查询渠道商下服务商流水信息
     *
     * @param agentMainId
     * @return
     */
    AgentMainTransactionVO getTransactionByAgentMainId(Long agentMainId);

    /**
     * 渠道商查询匹配好的服务商
     *
     * @param agentMainId
     * @return
     */
    List<AdminAgentMainServiceProviderListVO> getAgentMainServiceProviderList(Long agentMainId, IPage<AdminAgentMainServiceProviderListVO> page);


    /**
     * 合伙人查询流水信息
     *
     * @return
     */
    AgentMainTransactionVO getAllTransaction();

    /**
     * 查询合伙人可有用的服务商
     *
     * @return
     */
    List<PartnerServiceProviderListVO> getPartnerAllServiceProvider(IPage<PartnerServiceProviderListVO> page);

    /**
     * 商户端根据商户id查询总包
     */
    List<TotalInvoiceListEnterVO> queryTotalInvoiceListEnterprise(Long enterpriseId,String serviceProviderName,IPage<TotalInvoiceListEnterVO> page);

    /**
     * 查询和商户关联的服务商
     */
    List<RelationEnterpriseServiceVO> queryRelationEnterpriseService(Long enterpriseId, String serviceProviderName, IPage<RelationEnterpriseServiceVO> page);

    /**
     * 根据商户和服务商查询支付清单
     */
    List<EnterpriseServicePayListVO> queryEnterpriseServicePayList(Long enterpriseId, Long serviceProviderId, IPage<EnterpriseServicePayListVO> page);

    /**
     * 根据服务商查询汇总代开分包列表
     */
    List<EnterpriseSubcontractInvoiceVO> findServiceSubcontractSummary(Long serviceProviderId, String enterpriseName, InvoiceState companyInvoiceState, IPage<EnterpriseSubcontractInvoiceVO> page);


    /**
     * 根据支付清单id查询汇总代开的详情
     */
    InvoiceServiceDetailSummaryVO findServiceDetailSummary(Long payEnterpriseId);

    /**
     *
     */
    List<PayMakerListVO> getPayMakerLists(String payEnterpriseIds);

    /**
     * 查询总包支付清单详情
     *
     * @param payEnterpriseId
     * @return
     */
    PayEnterpriseDetailVO queryPayEnterpriseDetail(Long payEnterpriseId);

    /**
     * 查询编辑总包支付清单详情
     *
     * @param payEnterpriseId
     * @return
     */
    PayEnterpriseUpdateDetailVO queryPayEnterpriseUpdateDetail(Long payEnterpriseId);

}

