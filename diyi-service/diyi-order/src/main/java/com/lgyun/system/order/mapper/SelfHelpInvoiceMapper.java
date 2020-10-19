package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.SelfHelpInvoiceSpApplyState;
import com.lgyun.system.order.dto.SelfHelpInvoiceDetailsByServiceProviderDTO;
import com.lgyun.system.order.dto.SelfHelpInvoicesByEnterpriseDTO;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.order.vo.admin.SelfHelpInvoiceAdminDetailVO;
import com.lgyun.system.order.vo.admin.SelfHelpInvoiceAdminVO;
import com.lgyun.system.order.vo.maker.SelfHelpInvoiceYearMonthVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Mapper
public interface SelfHelpInvoiceMapper extends BaseMapper<SelfHelpInvoiceEntity> {

    /**
     * 查询条件查询所有自助开票记录
     *
     * @param enterpriseId
     * @param invoicePeopleType
     * @param selfHelpInvoicesByEnterpriseDto
     * @param page
     * @return
     */
    List<SelfHelpInvoiceListByEnterpriseVO> getSelfHelfInvoicesByEnterprise(Long enterpriseId, InvoicePeopleType invoicePeopleType, SelfHelpInvoicesByEnterpriseDTO selfHelpInvoicesByEnterpriseDto, IPage<SelfHelpInvoiceListByEnterpriseVO> page);

    /**
     * 查询当前商户某条自助开票记录详情
     *
     * @param enterpriseId
     * @param selfHelpInvoiceId
     * @return
     */
    SelfHelpInvoiceSingleByEnterpriseVO getSingleSelfHelfInvoiceByEnterprise(Long enterpriseId, Long selfHelpInvoiceId);

    /**
     * 查询当前商户某条自助开票记录的所有自助开票明细
     *
     * @param selfHelpInvoiceId
     * @return
     */
    List<SelfHelpInvoiceDetailListVO> getSelfHelfInvoiceDetailListBySelfHelfInvoice(Long selfHelpInvoiceId, IPage<SelfHelpInvoiceDetailListVO> page);

    /**
     * 查询当前商户某条自助开票记录的所有快递信息
     *
     * @param selfHelpInvoiceId
     * @param enterpriseId
     * @param page
     * @return
     */
    List<SelfHelpInvoiceExpressByEnterpriseProviderVO> getSelfHelfInvoiceExpressBySelfHelfInvoiceAndEnterprise(Long selfHelpInvoiceId, Long enterpriseId, IPage<SelfHelpInvoiceExpressByEnterpriseProviderVO> page);

    /**
     * 查询当前服务商所有自助开票明细记录
     *
     * @param serviceProviderId
     * @param invoicePeopleType
     * @param selfHelpInvoiceSpApplyState
     * @param selfHelpInvoiceDetailsByServiceProviderDto
     * @param page
     * @return
     */
    List<SelfHelpInvoiceListByServiceProviderVO> getSelfHelfInvoicesByServiceProvider(Long serviceProviderId, InvoicePeopleType invoicePeopleType, SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState, SelfHelpInvoiceDetailsByServiceProviderDTO selfHelpInvoiceDetailsByServiceProviderDto, IPage<SelfHelpInvoiceListByServiceProviderVO> page);

    /**
     * 查询当前服务商某条自助开票明细记录详情
     *
     * @param serviceProviderId
     * @param selfHelpInvoiceId
     * @return
     */
    SelfHelpInvoiceSingleByServiceProviderVO getSingleSelfHelfInvoiceByServiceProvider(Long serviceProviderId, Long selfHelpInvoiceId);

    /**
     * 查询当前服务商某条自助开票记录的快递信息
     *
     * @param selfHelpInvoiceId
     * @param serviceProviderId
     * @return
     */
    SelfHelpInvoiceExpressByEnterpriseProviderVO getSelfHelfInvoiceExpressBySelfHelfInvoiceAndProvider(Long selfHelpInvoiceId, Long serviceProviderId);

    /**
     * 查询个体户月度开票金额和年度开票金额
     *
     * @param allKindEnterpriseId
     * @param invoicePeopleType
     * @return
     */
    SelfHelpInvoiceYearMonthVO yearMonthMoney(Long allKindEnterpriseId, InvoicePeopleType invoicePeopleType);

    /**
     * 查询个体户月度开票金额和年度开票金额
     *
     * @param allKindEnterpriseId
     * @param invoicePeopleType
     * @return
     */
    SelfHelpInvoiceStatisticsVO selfHelpInvoiceStatistics(Long allKindEnterpriseId, InvoicePeopleType invoicePeopleType);

    /**
     * 查询个独或个体户开票记录
     *
     * @param allKindEnterpriseId
     * @param invoicePeopleType
     * @param page
     * @return
     */
    List<SelfHelpInvoiceListVO> selfHelpInvoiceList(Long allKindEnterpriseId, InvoicePeopleType invoicePeopleType, IPage<SelfHelpInvoiceListVO> page);

    /**
     * 根据商户查询众包/众采
     *
     * @param enterpriseId
     * @param serviceProviderName
     * @param page
     * @return
     */
    List<SelfHelpInvoiceCrowdSourcingVO> findEnterpriseCrowdSourcing(Long enterpriseId, String serviceProviderName, IPage<SelfHelpInvoiceCrowdSourcingVO> page);

    /**
     * 查询众包/众采详情
     *
     * @param selfHelpInvoiceId
     * @return
     */
    SelfHelpInvoiceCrowdSourcingVO findDetailCrowdSourcing(Long selfHelpInvoiceId);

    /**
     * 查询当前商户众包/众采年流水
     *
     * @param enterpriseId
     * @return
     */
    YearTradeVO queryCrowdYearTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商众包/众采年流水
     *
     * @param serviceProviderId
     * @return
     */
    YearTradeVO queryCrowdYearTradeByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户众包/众采本月流水
     *
     * @param enterpriseId
     * @return
     */
    MonthTradeVO queryCrowdMonthTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商众包本月流水
     *
     * @param serviceProviderId
     * @return
     */
    MonthTradeVO queryCrowdMonthTradeByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户众包本周流水
     *
     * @param enterpriseId
     * @return
     */
    WeekTradeVO queryCrowdWeekTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商众包本周流水
     *
     * @param serviceProviderId
     * @return
     */
    WeekTradeVO queryCrowdWeekTradeByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户众包今日流水
     *
     * @param enterpriseId
     * @return
     */
    DayTradeVO queryCrowdDayTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商众包今日流水
     *
     * @param serviceProviderId
     * @return
     */
    DayTradeVO queryCrowdDayTradeByServiceProvider(Long serviceProviderId);

    /**
     * 服务商查询众包发票
     */
    List<SelfHelpInvoiceCrowdSourcingVO> getServiceCrowdSour(Long serviceProviderId, String enterpriseName,String startTime,String endTime,SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState,IPage<SelfHelpInvoiceCrowdSourcingVO> page);


    /**
     * 服务商查询众包发票
     */
    ServiceCrowdSourcingDetailVO getServiceCrowdSourDetails(Long providerSelfHelpInvoiceId);


    /**
     * 平台跟据创客身份查询自助开票
     */
    List<SelfHelpInvoiceAdminVO> getAdminMakerTypeSelfHelpInvoice(String enterpriseName, String startTime, String endTime, MakerType makerType, IPage<SelfHelpInvoiceAdminVO> page);

    /**
     *平台跟据创客身份查询自助开票详情
     */
    SelfHelpInvoiceAdminDetailVO getMakerTypeSelfHelpInvoiceDetails(Long selfHelpInvoiceId);
}

