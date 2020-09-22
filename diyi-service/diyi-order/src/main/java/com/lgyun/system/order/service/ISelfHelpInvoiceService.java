package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.SelfHelpInvoiceSpApplyState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.SelfHelpInvoiceDetailInvoiceTaxDto;
import com.lgyun.system.order.dto.SelfHelpInvoiceDetailsByServiceProviderDto;
import com.lgyun.system.order.dto.SelfHelpInvoiceExpressDto;
import com.lgyun.system.order.dto.SelfHelpInvoicesByEnterpriseDto;
import com.lgyun.system.order.dto.admin.ToExamineSelfHelpInvoiceDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.order.vo.admin.SelfHelpInvoiceAdminVO;
import com.lgyun.system.user.entity.ServiceProviderWorkerEntity;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface ISelfHelpInvoiceService extends BaseService<SelfHelpInvoiceEntity> {

    /**
     * 查询当前商户所有自助开票记录
     *
     * @param enterpriseId
     * @param invoicePeopleType
     * @param selfHelpInvoicesByEnterpriseDto
     * @param page
     * @return
     */
    R<IPage<SelfHelpInvoiceListByEnterpriseVO>> getSelfHelfInvoicesByEnterprise(Long enterpriseId, InvoicePeopleType invoicePeopleType, SelfHelpInvoicesByEnterpriseDto selfHelpInvoicesByEnterpriseDto, IPage<SelfHelpInvoiceListByEnterpriseVO> page);

    /**
     * 查询当前商户某条自助开票记录详情
     *
     * @param enterpriseId
     * @param selfHelpInvoiceId
     * @return
     */
    R<SelfHelpInvoiceSingleByEnterpriseVO> getSingleSelfHelfInvoiceByEnterprise(Long enterpriseId, Long selfHelpInvoiceId);

    /**
     * 查询当前商户某条自助开票记录的所有自助开票明细
     *
     * @param selfHelpInvoiceId
     * @param page
     * @return
     */
    R<IPage<SelfHelpInvoiceDetailListVO>> getSelfHelfInvoiceDetailListBySelfHelfInvoice(Long selfHelpInvoiceId, IPage<SelfHelpInvoiceDetailListVO> page);

    /**
     * 查询当前商户某条自助开票记录的所有快递信息
     *
     * @param enterpriseId
     * @param selfHelpInvoiceId
     * @param page
     * @return
     */
    R<IPage<SelfHelpInvoiceExpressByEnterpriseProviderVO>> getSelfHelfInvoiceExpressBySelfHelfInvoiceAndEnterprise(Long enterpriseId, Long selfHelpInvoiceId, IPage<SelfHelpInvoiceExpressByEnterpriseProviderVO> page);

    /**
     * 查询当前服务商所有自助开票记录
     *
     * @param serviceProviderId
     * @param invoicePeopleType
     * @param selfHelpInvoiceSpApplyState
     * @param selfHelpInvoiceDetailsByServiceProviderDto
     * @param page
     * @return
     */
    R<IPage<SelfHelpInvoiceListByServiceProviderVO>> getSelfHelfInvoicesByServiceProvider(Long serviceProviderId, InvoicePeopleType invoicePeopleType, SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState, SelfHelpInvoiceDetailsByServiceProviderDto selfHelpInvoiceDetailsByServiceProviderDto, IPage<SelfHelpInvoiceListByServiceProviderVO> page);

    /**
     * 查询当前服务商某条自助开票明细记录详情
     *
     * @param serviceProviderId
     * @param selfHelpInvoiceDetailId
     * @return
     */
    R<SelfHelpInvoiceSingleByServiceProviderVO> getSingleSelfHelfInvoiceByServiceProvider(Long serviceProviderId, Long selfHelpInvoiceDetailId);

    /**
     * 服务商自助开票上传发票税票
     *
     * @param serviceProviderWorkerEntity
     * @param selfHelpInvoiceDetailInvoiceTaxDto
     * @return
     */
    R<String> uploadInvoiceTaxByProvider(ServiceProviderWorkerEntity serviceProviderWorkerEntity, SelfHelpInvoiceDetailInvoiceTaxDto selfHelpInvoiceDetailInvoiceTaxDto);

    /**
     * 服务商自助开票填写快递信息
     *
     * @param serviceProviderWorkerEntity
     * @param selfHelpInvoiceExpressDto
     * @return
     */
    R<String> fillExpressByProvider(ServiceProviderWorkerEntity serviceProviderWorkerEntity, SelfHelpInvoiceExpressDto selfHelpInvoiceExpressDto);

    /**
     * 查询当前服务商某条自助开票记录的快递信息
     *
     * @param serviceProviderId
     * @param selfHelpInvoiceId
     * @return
     */
    R<SelfHelpInvoiceExpressByEnterpriseProviderVO> getSelfHelfInvoiceExpressBySelfHelfInvoiceAndProvider(Long serviceProviderId, Long selfHelpInvoiceId);

    /**
     * 查询开票次数，月度开票金额，年度开票金额和总开票金额
     *
     * @param businessEnterpriseId
     * @param invoicePeopleType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long businessEnterpriseId, InvoicePeopleType invoicePeopleType);

    /**
     * 查询自助开票记录
     *
     * @param page
     * @param businessEnterpriseId
     * @param invoicePeopleType
     * @return
     */
    R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(IPage<SelfHelpInvoiceListVO> page, Long businessEnterpriseId, InvoicePeopleType invoicePeopleType);


    /**
     * 查询月度开票金额和年度开票金额
     *
     * @param individualBusinessId
     * @param invoicePeopleType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long individualBusinessId, InvoicePeopleType invoicePeopleType);

    /**
     * 根据商户查询众包/众采
     *
     * @param enterpriseId
     * @param serviceProviderName
     * @param page
     * @return
     */
    R findEnterpriseCrowdSourcing(Long enterpriseId, String serviceProviderName, IPage<SelfHelpInvoiceCrowdSourcingVO> page);

    /**
     * 查询详情接口-众包/众采
     */
    R findDetailCrowdSourcing(Long selfHelpInvoiceId);

    /**
     * 自助开票审核
     *
     * @param serviceProviderId
     * @param selfHelpInvoiceId
     * @param applyState
     * @return
     */
    R<String> audit(Long serviceProviderId, Long selfHelpInvoiceId, SelfHelpInvoiceSpApplyState applyState);

    /**
     * 查询当前商户众包/众采年流水
     *
     * @param enterpriseId
     * @return
     */
    R<YearTradeVO> queryCrowdYearTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商众包/众采年流水
     *
     * @param serviceProviderId
     * @return
     */
    R<YearTradeVO> queryCrowdYearTradeByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户众包/众采本月流水
     *
     * @param enterpriseId
     * @return
     */
    R<MonthTradeVO> queryCrowdMonthTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商众包/众采本月流水
     *
     * @param serviceProviderId
     * @return
     */
    R<MonthTradeVO> queryCrowdMonthTradeByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户众包/众采本周流水
     *
     * @param enterpriseId
     * @return
     */
    R<WeekTradeVO> queryCrowdWeekTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商众包/众采本周流水
     *
     * @param serviceProviderId
     * @return
     */
    R<WeekTradeVO> queryCrowdWeekTradeByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户众包/众采今日流水
     *
     * @param enterpriseId
     * @return
     */
    R<DayTradeVO> queryCrowdDayTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商众包/众采今日流水
     *
     * @param serviceProviderId
     * @return
     */
    R<DayTradeVO> queryCrowdDayTradeByServiceProvider(Long serviceProviderId);

    /**
     * 服务商查询众包发票
     */
    R getServiceCrowdSour(Long serviceProviderId,String enterpriseName,String startTime,String endTime,SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState,IPage<SelfHelpInvoiceCrowdSourcingVO> page);

    /**
     * 服务商查询众包发票
     */
    R getServiceCrowdSourDetails(Long providerSelfHelpInvoiceId);

    /**
     * 服务商众包发票开票
     */
    R savePortalSignInvoice(String serviceProviderName,Long providerSelfHelpInvoiceId,String expressNo,String expressCompanyName,String invoiceScanPictures,String taxScanPictures);

    /**
     * 平台跟据创客身份查询自助开票
     */
    R getAdminMakerTypeSelfHelpInvoice(String enterpriseName, String startTime, String endTime, MakerType makerType,IPage<SelfHelpInvoiceAdminVO> page);

    /**
     * 平台跟据创客身份查询自助开票详情
     */
    R getMakerTypeSelfHelpInvoiceDetails(Long selfHelpInvoiceId);

    /**
     *平台审核自助开票
     */
    R toExamineSelfHelpInvoice(ToExamineSelfHelpInvoiceDto toExamineSelfHelpInvoiceDto);

    /**
     *平台上传支付回单和匹配服务商开票
     */
    R matchServiceProvider(Long selfHelpInvoiceId,Long selfHelpInvoiceFeeId,Long serviceProviderId,String payCertificate);

    /**
     * 平台上传快递
     */
    R uploadAdminExpress(Long selfHelpInvoiceId, Long serviceProviderId,String expressNo, String expressCompanyName);

    /**
     * 平台上传发票
     */
    R uploadAdminInvoice(Long selfHelpInvoiceApplyProviderDetailId, String invoiceScanPictures, String taxScanPictures);
}

