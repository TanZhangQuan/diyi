package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.system.order.dto.PayEnterpriseMakerListDto;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.vo.TransactionVO;
import org.apache.ibatis.annotations.Mapper;

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
     * 根据条件查询所有总包支付清单
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param payEnterpriseMakerListDto
     * @param page
     * @return
     */
    List<PayEnterpriseMakersListVO> getPayEnterprises(Long enterpriseId, Long serviceProviderId, PayEnterpriseMakerListDto payEnterpriseMakerListDto, IPage<PayEnterpriseMakersListVO> page);

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
    List<PayMakerListVO> getPayMakerListByPayEnterpriseId(Long payEnterpriseId, IPage<PayMakerListVO> page);

    /**
     * 根据当前服务商，商户ID查询总包支付清单
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param payEnterpriseMakerListDto
     * @param page
     * @return
     */
    List<PayEnterpriseMakersListVO> getPayEnterprisesByEnterprisesServiceProvider(Long enterpriseId, Long serviceProviderId, PayEnterpriseMakerListDto payEnterpriseMakerListDto, IPage<PayEnterpriseMakersListVO> page);

    /**
     * 获取当前商户首页交易情况数据
     *
     * @param enterpriseId
     * @return
     */
    TransactionVO transactionByEnterprise(Long enterpriseId);

    /**
     * 获取当前服务商首页交易情况数据
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
     * 服务商查询总包发票
     * @param serviceProviderId
     * @param enterpriseName
     * @param startTime
     * @param endTime
     * @param page
     * @return
     */
    List<InvoiceServiceLumpVO> getServiceLumpSumInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime, InvoiceState companyInvoiceState, IPage<InvoiceServiceLumpVO> page);


    /**
     * 服务商查询总包发票详情
     * @param payEnterpriseId
     * @return
     */
    List<InvoiceServiceLumpDetailsVO> getServiceLumpSumInvoiceDetails(Long payEnterpriseId);


    /**
     * 服务商查询未开票分包发票
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
     * @param payEnterpriseId
     * @return
     */
   InvoiceServiceSubDetailsVO getSubcontractInvoiceDetails(Long payEnterpriseId);


    /**
     *服务商查询已开票的汇总代开发票
     */
    List<InvoiceServiceSubVO> getServiceSummaryInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime,IPage<InvoiceServiceSubVO> page);


    /**
     * 服务商查询已开票的门征代开发票
     */
    List<InvoiceServiceSubVO> getServicePortalSignInvoice(Long serviceProviderId, String enterpriseName, String startTime, String endTime,IPage<InvoiceServiceSubVO> page);
}

