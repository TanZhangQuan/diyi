package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.order.vo.admin.SelfHelpInvoiceDetailAdminVO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
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
     * @param invoicePeopleType
     * @param makerId
     * @param year
     * @param month
     * @return
     */
    AllIncomeYearMonthVO queryCrowdNumAndAllIncome(InvoicePeopleType invoicePeopleType, Long makerId, Long year, Long month);

    /**
     * 根据开票人身份类别查询当前创客众包的每年收入
     *
     * @param invoicePeopleType
     * @param makerId
     * @return
     */
    IncomeYearVO queryEveryYearCrowdIncome(InvoicePeopleType invoicePeopleType, Long makerId);

    /**
     * 根据开票人身份类别，年份查询每月收入
     *
     * @param invoicePeopleType
     * @param makerId
     * @param year
     * @return
     */
    YearTradeVO queryEveryMonthCrowdIncome(InvoicePeopleType invoicePeopleType, Long makerId, Long year);

    /**
     * 根据开票人身份类别，年份，月份（可选）查询创客对应商户众包的收入金额
     *
     * @param invoicePeopleType
     * @param makerId
     * @param year
     * @param month
     * @param page
     * @return
     */
    List<AllIncomeYearMonthEnterpriseVO> queryMakerToEnterpriseCrowdIncome(InvoicePeopleType invoicePeopleType, Long makerId, Long year, Long month, IPage<AllIncomeYearMonthEnterpriseVO> page);

    /**
     * 根据开票人身份类别，年份，月份，商户编号（可选）查询众包收入明细
     *
     * @param invoicePeopleType
     * @param makerId
     * @param year
     * @param month
     * @param enterpriseId
     * @param page
     * @return
     */
    List<IncomeDetailYearMonthVO> queryCrowdIncomeDetail(InvoicePeopleType invoicePeopleType, Long makerId, Long year, Long month, Long enterpriseId, IPage<IncomeDetailYearMonthVO> page);

    /**
     * 根据工单类型，创客类型，年份，月份，商户编号（可选）查询明细总收入
     *
     * @param invoicePeopleType
     * @param makerId
     * @param year
     * @param month
     * @param enterpriseId
     * @return
     */
    BigDecimal queryCrowdDetailAllIncome(InvoicePeopleType invoicePeopleType, Long makerId, Long year, Long month, Long enterpriseId);

    List<SelfHelpInvoiceDetailAdminVO> getSelfHelpInvoiceIdAll(Long selfHelpInvoiceId);
}

