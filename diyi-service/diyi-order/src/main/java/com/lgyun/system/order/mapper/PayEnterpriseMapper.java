package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.CompanyInvoiceState;
import com.lgyun.system.order.dto.PayEnterpriseDTO;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.dto.PayEnterpriseListSimpleDTO;
import com.lgyun.system.user.vo.TransactionVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author tzq
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
     * 根据条件查询所有总包支付清单
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param payEnterpriseDto
     * @param page
     * @return
     */
    List<PayEnterpriseListVO> getPayEnterpriseList(Long enterpriseId, Long serviceProviderId, PayEnterpriseDTO payEnterpriseDto, IPage<PayEnterpriseListVO> page);

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
     * 根据支付清单ID查询分包支付明细
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    List<PayMakerListInvoiceVO> queryPayMakerListInvoice(Long payEnterpriseId, IPage<PayMakerListInvoiceVO> page);

    /**
     * 根据支付清单ID查询分包支付明细
     *
     * @param payEnterpriseId
     * @param page
     * @return
     */
    List<PayMakerListWebVO> getPayMakerListByPayEnterprise(Long payEnterpriseId, IPage<PayMakerListWebVO> page);

    /**
     * 查询当前商户首页交易情况数据
     *
     * @param enterpriseId
     * @return
     */
    TransactionVO queryEnterpriseTransaction(Long enterpriseId);

    /**
     * 查询当前服务商首页交易情况数据
     *
     * @param serviceProviderId
     * @return
     */
    TransactionVO queryServiceProviderTransaction(Long serviceProviderId);

    /**
     * 查询渠道商-商户交易数据
     *
     * @param agentMainId
     * @return
     */
    TransactionVO queryAgentMainEnterpriseTransaction(Long agentMainId);

    /**
     * 查询渠道商-服务商交易数据
     *
     * @param agentMainId
     * @return
     */
    TransactionVO queryAgentMainServiceProviderTransaction(Long agentMainId);

    /**
     * 查询合伙人-服务商交易数据
     *
     * @param partnerId
     * @return
     */
    TransactionVO queryPartnerEnterpriseTransaction(Long partnerId);

    /**
     * 查询相关局-服务商交易情况数据
     *
     * @param relBureauId
     * @return
     */
    TransactionVO queryRelBureauServiceProviderTransaction(Long relBureauId);

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
     * 服务商查询已开票的汇总代开发票
     */
    List<InvoiceServiceSubVO> getServiceSummaryInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, IPage<InvoiceServiceSubVO> page);

    /**
     * 商户端根据商户id查询总包
     */
    List<TotalInvoiceListEnterVO> queryTotalInvoiceListEnterprise(Long enterpriseId, String serviceProviderName, IPage<TotalInvoiceListEnterVO> page);

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
    List<EnterpriseSubcontractInvoiceVO> findServiceSubcontractSummary(Long serviceProviderId, String enterpriseName, CompanyInvoiceState companyInvoiceState, IPage<EnterpriseSubcontractInvoiceVO> page);


    /**
     * 根据支付清单id查询汇总代开的详情
     */
    InvoiceServiceDetailSummaryVO findServiceDetailSummary(Long payEnterpriseId);

    /**
     *
     */
    List<PayMakerListServiceProviderVO> getPayMakerLists(String payEnterpriseIds);

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

    /**
     * 商户端根据商户id查询总包申请的详情总包支付清单
     *
     * @param invoiceApplicationId
     * @return
     */
    List<EnterpriseApplyDetailVO> queryTotalInvoiceListEnterpriseApplyDetails(Long invoiceApplicationId);

    /**
     * 商户端根据商户id查询总包开票的详情总包支付清单
     */
    List<EnterpriseInvoiceDetailVO> queryTotalInvoiceListEnterpriseInvoiceDetails(Long invoicePrintId);

    /**
     * 总包合并开票详情
     *
     * @param payEnterpriseIds
     * @return
     */
    List<TotalMergeInvoiceVO> queryTotalMergeInvoice(String payEnterpriseIds);

    /**
     * 查询商户总包+分包
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param payEnterpriseListSimpleDTO
     * @param page
     * @return
     */
    List<PayEnterpriseListSimpleVO> queryPayEnterpriseListAgentMain(Long enterpriseId, Long serviceProviderId, PayEnterpriseListSimpleDTO payEnterpriseListSimpleDTO, IPage<PayEnterpriseListSimpleVO> page);

    /**
     * 查询总包支付清单物流信息
     *
     * @param payEnterpriseId
     * @return
     */
    PayEnterpriseExpressVO queryPayEnterpriseExpress(Long payEnterpriseId);
}

