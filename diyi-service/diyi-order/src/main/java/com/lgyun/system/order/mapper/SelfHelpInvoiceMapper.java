package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.SelfHelpInvoiceSpApplyState;
import com.lgyun.system.order.dto.SelfHelpInvoiceDetailsByServiceProviderDTO;
//import com.lgyun.system.order.dto.SelfHelpInvoicesByEnterpriseDTO;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.order.vo.TotalCrowdTradeListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * Mapper
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Mapper
public interface SelfHelpInvoiceMapper extends BaseMapper<SelfHelpInvoiceEntity> {

//    /**
//     * 查询条件查询所有自助开票记录
//     *
//     * @param enterpriseId
//     * @param makerType
//     * @param selfHelpInvoicesByEnterpriseDto
//     * @param page
//     * @return
//     */
//    List<SelfHelpInvoiceListByEnterpriseVO> getSelfHelfInvoicesByEnterprise(Long enterpriseId, MakerType makerType, SelfHelpInvoicesByEnterpriseDTO selfHelpInvoicesByEnterpriseDto, IPage<SelfHelpInvoiceListByEnterpriseVO> page);

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
     * @param makerType
     * @param selfHelpInvoiceSpApplyState
     * @param selfHelpInvoiceDetailsByServiceProviderDto
     * @param page
     * @return
     */
    List<SelfHelpInvoiceListByServiceProviderVO> getSelfHelfInvoicesByServiceProvider(Long serviceProviderId, MakerType makerType, SelfHelpInvoiceSpApplyState selfHelpInvoiceSpApplyState, SelfHelpInvoiceDetailsByServiceProviderDTO selfHelpInvoiceDetailsByServiceProviderDto, IPage<SelfHelpInvoiceListByServiceProviderVO> page);

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
     * @param individualId
     * @param makerType
     * @return
     */
    SelfHelpInvoiceStatisticsVO selfHelpInvoiceStatistics(Long individualId, MakerType makerType);

    /**
     * 查询个独或个体户开票记录
     *
     * @param individualId
     * @param makerType
     * @param page
     * @return
     */
    List<SelfHelpInvoiceListVO> selfHelpInvoiceList(Long individualId, MakerType makerType, IPage<SelfHelpInvoiceListVO> page);

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
     * 查询众包/众采流水
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param relBureauId
     * @param timeType
     * @return
     */
    List<TradeVO> queryCrowdTrade(Long enterpriseId, Long serviceProviderId, Long relBureauId, String timeType, Date beginDate, Date endDate);

    /**
     * 平台跟据创客身份查询自助开票
     */
    List<SelfHelpInvoiceAdminVO> getAdminMakerTypeSelfHelpInvoice(String enterpriseName, String startTime, String endTime, MakerType makerType, IPage<SelfHelpInvoiceAdminVO> page);

    /**
     * 平台跟据创客身份查询自助开票详情
     */
    SelfHelpInvoiceAdminDetailVO getMakerTypeSelfHelpInvoiceDetails(Long selfHelpInvoiceId);

    /**
     * 查询当前服务商的自助开票
     *
     * @param serviceProviderId
     * @param keyword
     * @param page
     * @return
     */
    List<SelfHelpInvoiceSerProVO> querySelfHelpInvoiceList(Long serviceProviderId, String keyword, IPage<SelfHelpInvoiceSerProVO> page);

    /**
     * 根据当前服务商和自助开票ID查询自助开票详情
     *
     * @param selfHelpvoiceId
     * @param page
     * @return
     */
    List<SelfHelpInvoiceDetailProviderVO> querySelfHelpInvoicePeopleList(Long selfHelpvoiceId, IPage<SelfHelpInvoiceDetailProviderVO> page);


    /**
     *商户查询自助开票
     */
    List<SelfInvoiceListVO> querySelfInvoiceList(Long enterpriseId, MakerType makerType, String startTiem, String endTime, IPage<SelfInvoiceListVO> page);

    /**
     * 创客查询
     */
    List<SelfInvoiceListVO> queryMakerSelfInvoiceList(Long makerId, MakerType makerType, String startTiem, String endTime, IPage<SelfInvoiceListVO> page);


    /**
     * 服务商查询自助开票
     */
    List<SelfInvoiceListVO> queryServiceProviderSelfInvoiceList(Long serviceProviderId, MakerType makerType, String startTiem, String endTime, IPage<SelfInvoiceListVO> page);

    /**
     * 平台查询自助开票
     */
    List<SelfInvoiceListVO> queryAdminSelfInvoiceList(MakerType makerType, String startTiem, String endTime, IPage<SelfInvoiceListVO> page);

    /**
     *
     */
    List<SelfInvoiceDetailVO> querySelfInvoiceDetail(Long selfHelpInvoiceId);

    /**
     * 查询相关局众包/众采列表
     *
     * @param relBureauId
     * @param page
     * @return
     */
    List<TotalCrowdTradeListVO> queryRelBureauCrowdList(Long relBureauId, IPage<TotalCrowdTradeListVO> page);
}

