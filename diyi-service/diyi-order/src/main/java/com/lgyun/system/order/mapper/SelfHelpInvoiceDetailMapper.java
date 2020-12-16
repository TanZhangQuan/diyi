package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Mapper
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Mapper
public interface SelfHelpInvoiceDetailMapper extends BaseMapper<SelfHelpInvoiceDetailEntity> {

    /**
     * 根据开票人身份类别，年份，月份（可选）查询当前创客总包+分包的笔数和收入金额
     *
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @return
     */
    AllIncomeYearMonthVO queryCrowdNumAndAllIncome(MakerType makerType, Long makerId, Long year, Long month);

    /**
     * 查询创客众包/众采收入
     *
     * @param makerType
     * @param makerId
     * @param timeType
     * @param year
     * @param beginDate
     * @param endDate
     * @return
     */
    List<TradeVO> queryCrowdMakerIncome(MakerType makerType, Long makerId, String timeType, Date year, Date beginDate, Date endDate);

    /**
     * 根据开票人身份类别，年份，月份（可选）查询创客对应商户众包的收入金额
     *
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @param page
     * @return
     */
    List<AllIncomeYearMonthEnterpriseVO> queryMakerToEnterpriseCrowdIncome(MakerType makerType, Long makerId, Long year, Long month, IPage<AllIncomeYearMonthEnterpriseVO> page);

    /**
     * 根据开票人身份类别，年份，月份，商户编号（可选）查询众包收入明细
     *
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @param enterpriseId
     * @param page
     * @return
     */
    List<IncomeDetailYearMonthVO> queryCrowdIncomeDetail(MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId, IPage<IncomeDetailYearMonthVO> page);

    /**
     * 根据工单类型，创客类型，年份，月份，商户编号（可选）查询明细总收入
     *
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @param enterpriseId
     * @return
     */
    BigDecimal queryCrowdDetailAllIncome(MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId);

    List<SelfHelpInvoiceDetailAdminVO> getSelfHelpInvoiceIdAll(Long selfHelpInvoiceId);

    /**
     * 查询众包交付支付验收单的自主开票明细
     *
     * @param acceptPaysheetCsId
     * @param page
     * @return
     */
    List<AcceptPaysheetCsSelfHelpInvoiceDetailListVO> queryCrowdAcceptPaysheetSelfHelpInvoiceDetailList(Long acceptPaysheetCsId, IPage<AcceptPaysheetCsSelfHelpInvoiceDetailListVO> page);

    /**
     * 查询创客自主开票明细
     *
     * @param makerId
     * @param page
     * @return
     */
    List<SelfHelpInvoiceListMakerVO> querySelfHelpInvoiceDetailListByMaker(Long makerId, IPage<SelfHelpInvoiceListMakerVO> page);

}

