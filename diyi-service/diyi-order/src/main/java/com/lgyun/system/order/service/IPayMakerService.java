package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.lgyun.system.order.excel.PayEnterpriseExcel;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.user.vo.MakerEnterpriseNumIncomeVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
public interface IPayMakerService extends BaseService<PayMakerEntity> {

    /**
     * 查询当前创客关联商户数和收入情况
     *
     * @param makerId
     * @return
     */
    R<MakerEnterpriseNumIncomeVO> getEnterpriseNumIncome(Long makerId);

    /**
     * 根据创客类型，年份，月份（可选）查询当前创客总包+分包的笔数和总收入金额
     *
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @return
     */
    R<AllIncomeYearMonthVO> queryTotalSubNumAndAllIncome(MakerType makerType, Long makerId, Long year, Long month);

    /**
     * 根据创客类型查询当前创客总包+分包的每年收入
     *
     * @param makerType
     * @param makerId
     * @return
     */
    R<IncomeYearVO> queryEveryYearTotalSubIncome(MakerType makerType, Long makerId);

    /**
     * 根据创客类型，年份查询每月收入
     *
     * @param makerType
     * @param makerId
     * @param year
     * @return
     */
    R<YearTradeVO> queryEveryMonthTotalSubIncome(MakerType makerType, Long makerId, Long year);

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
    R<IPage<AllIncomeYearMonthEnterpriseVO>> queryMakerToEnterpriseTotalSubIncome(MakerType makerType, Long makerId, Long year, Long month, IPage<AllIncomeYearMonthEnterpriseVO> page);

    /**
     * 根据创客类型，年份，月份，商户编号（可选）查询总包+分包收入明细
     *
     * @param makerType
     * @param id
     * @param year
     * @param month
     * @param enterpriseId
     * @param page
     * @return
     */
    R<IPage<IncomeDetailYearMonthVO>> queryTotalSubIncomeDetail(MakerType makerType, Long id, Long year, Long month, Long enterpriseId, IPage<IncomeDetailYearMonthVO> page);

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
    R<BigDecimal> queryTotalSubDetailAllIncome(MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId);

    /**
     * 根据商户支付清单id查询创客支付明细
     *
     * @param payEnterpriseId
     * @return
     */
    List<PayMakerVO> getPayEnterpriseId(Long payEnterpriseId);

    /**
     * 根据商户支付清单id查询创客支付明细和门征发票信息
     *
     * @param payEnterpriseId
     * @return
     */
    List<PayMakerVO> getPayEnterprise(Long payEnterpriseId);

    /**
     * 根据总包支付清单生成分包
     *
     * @param list
     * @param payEnterpriseId
     * @param makerType
     */
    void importPayMakerList(List<PayEnterpriseExcel> list, Long payEnterpriseId, MakerType makerType, Long enterpriseId);

    /**
     * 查询个体户/个独月度开票金额和年度开票金额
     *
     * @param individualBusinessId
     * @param makerType
     * @return
     */
    R<IndividualYearMonthVO> yearMonthMoney(Long individualBusinessId, MakerType makerType);

    /**
     * 删除创客支付明细
     *
     * @param payEnterpriseId
     * @return
     */
    void deleteByPayEnterpriseId(Long payEnterpriseId);

    /**
     * 查询总包+分包交付支付验收单的创客支付明细
     *
     * @param acceptPaysheetId
     * @param page
     * @return
     */
    R<IPage<AcceptPaysheetPayMakerListVO>> queryTotalSubAcceptPaysheetPayMakerList(Long acceptPaysheetId, IPage<AcceptPaysheetPayMakerListVO> page);

    /**
     * 确认创客支付明细
     *
     * @param makerId
     * @param payMakerId
     * @return
     */
    R<String> confirmPayMaker(Long makerId, Long payMakerId);

    /**
     * 根据商户明细id
     *
     * @param payEnterpriseIds
     * @return
     */
    Integer getPayMakerCount(String payEnterpriseIds);

    /**
     * 查询创客的支付明细
     *
     * @param makerId
     * @param page
     * @return
     */
    R<IPage<PayMakerListMakerVO>> queryPayMakerListByMaker(Long makerId, IPage<PayMakerListMakerVO> page);

    /**
     * 查询大于七天的没验收的单子
     */
    List<ExceedPayMakerListVO>  queryExceedPayMakerList(int days);
}

