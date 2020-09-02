package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.system.order.dto.SelfHelpInvoicePayDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.vo.*;
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
     * 查询开票详情
     *
     * @param selfHelpInvoiceId
     * @return
     */
    SelfHelpInvoiceDetailsVO getSelfHelpInvoiceDetails(Long selfHelpInvoiceId);

    /**
     * 查询个体户月度开票金额和年度开票金额
     *
     * @param allKindEnterpriseId
     * @param invoicePeopleType
     * @return
     */
    SelfHelpInvoiceStatisticsVO yearMonthMoney(Long allKindEnterpriseId, InvoicePeopleType invoicePeopleType);

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
     * 根据商户ID, 开票客户类型查询自助开票
     *
     * @param enterpriseId
     * @param makerType
     * @param page
     * @return
     */
    List<SelfHelpInvoiceDetailsVO> findMakerTypeSelfHelpInvoice(Long enterpriseId, MakerType makerType,String invoicePeopleName,String startTime,String endTime, IPage<SelfHelpInvoiceDetailsVO> page);

    /**
     * 查询当前商户所有自主开票记录(众包)
     *
     * @param enterpriseId
     * @param selfHelpInvoicePayDto
     * @param page
     * @return
     */
    List<SelfHelpInvoicePayVO> getSelfHelfInvoiceByEnterpriseId(Long enterpriseId, SelfHelpInvoicePayDto selfHelpInvoicePayDto, IPage<SelfHelpInvoicePayVO> page);

    /**
     * 根据商户查询众包
     * @param enterpriseId
     * @param serviceProviderName
     * @param page
     * @return
     */
    List<SelfHelpInvoiceCrowdSourcingVO> findEnterpriseCrowdSourcing(Long enterpriseId,String serviceProviderName,IPage<SelfHelpInvoiceCrowdSourcingVO> page);

    /**
     * 查询众包详情
     * @param selfHelpInvoiceId
     * @return
     */
    SelfHelpInvoiceCrowdSourcingVO findDetailCrowdSourcing(Long selfHelpInvoiceId);

    /**
     *
     */
    List<SelfHelpInvoiceCrowdSourcingVO> selectEntMakSourc(Long enterpriseId,IPage<SelfHelpInvoiceCrowdSourcingVO> page);

    /**
     * 查询当前服务商的所有众包/众采
     *
     * @param serviceProviderId
     * @param selfHelpInvoicePayDto
     * @param page
     * @return
     */
    List<SelfHelpInvoicePayVO> findSelfHelpInvoiceByServiceProvider(Long serviceProviderId, SelfHelpInvoicePayDto selfHelpInvoicePayDto, IPage<SelfHelpInvoicePayVO> page);

    /**
     * 查询当前商户众包年流水
     *
     * @param enterpriseId
     * @return
     */
    YearTradeVO queryCrowdYearTradeByEnterprise(Long enterpriseId);

    /**
     * 查询当前服务商众包年流水
     *
     * @param serviceProviderId
     * @return
     */
    YearTradeVO queryCrowdYearTradeByServiceProvider(Long serviceProviderId);

    /**
     * 查询当前商户众包本月流水
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

}

