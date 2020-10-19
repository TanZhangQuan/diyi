package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.SelfHelpInvoiceSpApplyState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.SelfHelpInvoiceDetailInvoiceTaxDTO;
import com.lgyun.system.order.dto.SelfHelpInvoiceDetailsByServiceProviderDTO;
import com.lgyun.system.order.dto.SelfHelpInvoiceExpressDTO;
import com.lgyun.system.order.dto.SelfHelpInvoicesByEnterpriseDTO;
import com.lgyun.system.order.dto.admin.ToExamineSelfHelpInvoiceDTO;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.order.vo.admin.SelfHelpInvoiceAdminVO;
import com.lgyun.system.order.vo.maker.SelfHelpInvoiceYearMonthVO;
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
    R<IPage<SelfHelpInvoiceListByEnterpriseVO>> getSelfHelfInvoicesByEnterprise(Long enterpriseId, InvoicePeopleType invoicePeopleType, SelfHelpInvoicesByEnterpriseDTO selfHelpInvoicesByEnterpriseDto, IPage<SelfHelpInvoiceListByEnterpriseVO> page);

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
    R<IPage<SelfHelpInvoiceListByServiceProviderVO>> getSelfHelfInvoicesByServiceProvider(Long serviceProviderId, InvoicePeopleType invoicePeopleType, SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState, SelfHelpInvoiceDetailsByServiceProviderDTO selfHelpInvoiceDetailsByServiceProviderDto, IPage<SelfHelpInvoiceListByServiceProviderVO> page);

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
    R<String> uploadInvoiceTaxByProvider(ServiceProviderWorkerEntity serviceProviderWorkerEntity, SelfHelpInvoiceDetailInvoiceTaxDTO selfHelpInvoiceDetailInvoiceTaxDto);

    /**
     * 服务商自助开票填写快递信息
     *
     * @param serviceProviderWorkerEntity
     * @param selfHelpInvoiceExpressDto
     * @return
     */
    R<String> fillExpressByProvider(ServiceProviderWorkerEntity serviceProviderWorkerEntity, SelfHelpInvoiceExpressDTO selfHelpInvoiceExpressDto);

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
    R<SelfHelpInvoiceYearMonthVO> yearMonthMoney(Long individualBusinessId, InvoicePeopleType invoicePeopleType);

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
     *
     * @param selfHelpInvoiceId
     * @return
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
     *
     * @param serviceProviderId
     * @param enterpriseName
     * @param startTime
     * @param endTime
     * @param selfHelpInvoiceSpApplyState
     * @param page
     * @return
     */
    R getServiceCrowdSour(Long serviceProviderId,String enterpriseName,String startTime,String endTime,SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState,IPage<SelfHelpInvoiceCrowdSourcingVO> page);

    /**
     * 服务商查询众包发票
     *
     * @param providerSelfHelpInvoiceId
     * @return
     */
    R getServiceCrowdSourDetails(Long providerSelfHelpInvoiceId);

    /**
     * 服务商众包发票开票
     *
     * @param serviceProviderName
     * @param providerSelfHelpInvoiceId
     * @param expressNo
     * @param expressCompanyName
     * @param invoiceScanPictures
     * @param taxScanPictures
     * @return
     */
    R savePortalSignInvoice(String serviceProviderName,Long providerSelfHelpInvoiceId,String expressNo,String expressCompanyName,String invoiceScanPictures,String taxScanPictures);

    /**
     * 平台跟据创客身份查询自助开票
     *
     * @param enterpriseName
     * @param startTime
     * @param endTime
     * @param makerType
     * @param page
     * @return
     */
    R getAdminMakerTypeSelfHelpInvoice(String enterpriseName, String startTime, String endTime, MakerType makerType,IPage<SelfHelpInvoiceAdminVO> page);

    /**
     * 平台跟据创客身份查询自助开票详情
     *
     * @param selfHelpInvoiceId
     * @return
     */
    R getMakerTypeSelfHelpInvoiceDetails(Long selfHelpInvoiceId);

    /**
     * 平台审核自助开票
     *
     * @param toExamineSelfHelpInvoiceDto
     * @return
     */
    R toExamineSelfHelpInvoice(ToExamineSelfHelpInvoiceDTO toExamineSelfHelpInvoiceDto);

    /**
     * 平台上传支付回单和匹配服务商开票
     *
     * @param selfHelpInvoiceId
     * @param selfHelpInvoiceFeeId
     * @param serviceProviderId
     * @param payCertificate
     * @return
     */
    R matchServiceProvider(Long selfHelpInvoiceId,Long selfHelpInvoiceFeeId,Long serviceProviderId,String payCertificate);

    /**
     * 平台上传快递
     *
     * @param selfHelpInvoiceId
     * @param serviceProviderId
     * @param expressNo
     * @param expressCompanyName
     * @return
     */
    R uploadAdminExpress(Long selfHelpInvoiceId, Long serviceProviderId,String expressNo, String expressCompanyName);

    /**
     * 平台上传发票
     *
     * @param selfHelpInvoiceApplyProviderDetailId
     * @param invoiceScanPictures
     * @param taxScanPictures
     * @return
     */
    R uploadAdminInvoice(Long selfHelpInvoiceApplyProviderDetailId, String invoiceScanPictures, String taxScanPictures);
}

