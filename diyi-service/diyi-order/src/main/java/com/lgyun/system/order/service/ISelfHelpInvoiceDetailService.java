package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.TimeType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.excel.InvoiceListExcel;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailAdminVO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface ISelfHelpInvoiceDetailService extends BaseService<SelfHelpInvoiceDetailEntity> {

    /**
     * 根据开票人身份类别，年份，月份（可选）查询当前创客总包+分包的笔数和总收入金额
     *
     * @param makerType
     * @param makerId
     * @param year
     * @param month
     * @return
     */
    R<AllIncomeYearMonthVO> queryCrowdNumAndAllIncome(MakerType makerType, Long makerId, Long year, Long month);

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
    R<List<TradeVO>> queryCrowdMakerIncome(MakerType makerType, Long makerId, TimeType timeType, Date year, Date beginDate, Date endDate);

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
    R<IPage<AllIncomeYearMonthEnterpriseVO>> queryMakerToEnterpriseCrowdIncome(MakerType makerType, Long makerId, Long year, Long month, IPage<AllIncomeYearMonthEnterpriseVO> page);

    /**
     * 根据开票人身份类别，年份，月份，商户编号（可选）查询众包收入明细
     *
     * @param makerType
     * @param id
     * @param year
     * @param month
     * @param enterpriseId
     * @param page
     * @return
     */
    R<IPage<IncomeDetailYearMonthVO>> queryCrowdIncomeDetail(MakerType makerType, Long id, Long year, Long month, Long enterpriseId, IPage<IncomeDetailYearMonthVO> page);

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
    R<BigDecimal> queryCrowdDetailAllIncome(MakerType makerType, Long makerId, Long year, Long month, Long enterpriseId);

    R<String> uploadDeliverSheetUrl(Long selfHelpInvoiceDetailId, String deliverSheetUrl);

    void importSelfHelpInvoiceDetail(List<InvoiceListExcel> list);

    /**
     * 根据自主开票主表Id查询明细是否已全部开票
     *
     * @param selfHelpInvoiceId
     * @param selfHelpInvoiceDetailId
     * @return
     */
    Boolean getSelfHelpInvoiceDetails(Long selfHelpInvoiceId, Long selfHelpInvoiceDetailId);

    /**
     * 根据主表id查询所有明细
     *
     * @param selfHelpInvoiceId
     * @return
     */
    List<SelfHelpInvoiceDetailVO> getSelfHelpInvoiceId(Long selfHelpInvoiceId);

    List<SelfHelpInvoiceDetailAdminVO> getSelfHelpInvoiceIdAll(Long selfHelpInvoiceId);

    /**
     * 查询众包交付支付验收单的自主开票明细
     *
     * @param acceptPaysheetCsId
     * @param page
     * @return
     */
    R<IPage<AcceptPaysheetCsSelfHelpInvoiceDetailListVO>> queryCrowdAcceptPaysheetSelfHelpInvoiceDetailList(Long acceptPaysheetCsId, IPage<AcceptPaysheetCsSelfHelpInvoiceDetailListVO> page);

    /**
     * 查询创客自主开票明细
     *
     * @param makerId
     * @param page
     * @return
     */
    R<IPage<SelfHelpInvoiceListMakerVO>> querySelfHelpInvoiceDetailListByMaker(Long makerId, IPage<SelfHelpInvoiceListMakerVO> page);
}

