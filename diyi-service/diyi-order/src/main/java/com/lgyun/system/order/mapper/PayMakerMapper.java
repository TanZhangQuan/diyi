package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.order.vo.maker.IndividualYearMonthVO;
import com.lgyun.system.user.vo.MakerEnterpriseNumIncomeVO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
@Mapper
public interface PayMakerMapper extends BaseMapper<PayMakerEntity> {

    /**
     * 查询当前创客关联商户数和收入情况
     *
     * @param makerId
     * @return
     */
    MakerEnterpriseNumIncomeVO getEnterpriseNumIncome(Long makerId);

    /**
     * 根据创客类型，年份，月份（可选）查询当前创客总包+分包的笔数和总收入金额
     *
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @return
     */
    AllIncomeYearMonthVO queryTotalSubNumAndAllIncome(MakerType makerType, Long makerId, Long year, Long month);

    /**
     * 根据创客类型查询当前创客总包+分包的每年收入
     *
     * @param makerType
     * @param makerId
     * @return
     */
    IncomeYearVO queryEveryYearTotalSubIncome(MakerType makerType, Long makerId);

    /**
     * 根据创客类型，年份查询每月收入
     *
     * @param makerType
     * @param makerId
     * @param year
     * @return
     */
    YearTradeVO queryEveryMonthTotalSubIncome(MakerType makerType, Long makerId, Long year);

    /**
     * 根据创客类型，年份，月份（可选）查询创客对应商户总包+分包的收入金额
     *
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @param page
     * @return
     */
    List<AllIncomeYearMonthEnterpriseVO> queryMakerToEnterpriseTotalSubIncome(MakerType makerType, Long makerId, Long year, Long month, IPage<AllIncomeYearMonthEnterpriseVO> page);

    /**
     * 根据创客类型，年份，月份，商户编号（可选）查询总包+分包收入明细
     *
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @param enterpriseId
     * @param page
     * @return
     */
    List<IncomeDetailYearMonthVO> queryTotalSubIncomeDetail(MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId, IPage<IncomeDetailYearMonthVO> page);

    /**
     * 根据创客类型，年份，月份，商户编号（可选）查询总包+分包明细的总收入
     *
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @param enterpriseId
     * @return
     */
    BigDecimal queryTotalSubDetailAllIncome(MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId);

    /**
     * 查询个体户/个独月度开票金额和年度开票金额
     *
     * @param allKindEnterpriseId
     * @param invoicePeopleType
     * @return
     */
    IndividualYearMonthVO yearMonthMoney(Long allKindEnterpriseId, InvoicePeopleType invoicePeopleType);

}

