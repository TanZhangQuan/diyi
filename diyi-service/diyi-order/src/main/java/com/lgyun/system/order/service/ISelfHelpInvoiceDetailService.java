package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.excel.InvoiceListExcel;
import com.lgyun.system.order.vo.*;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailAdminVO;

import java.math.BigDecimal;
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
     * @param invoicePeopleType
     * @param makerId
     * @param year
     * @param month
     * @return
     */
    R<AllIncomeYearMonthVO> queryCrowdNumAndAllIncome(InvoicePeopleType invoicePeopleType, Long makerId, Long year, Long month);

    /**
     * 根据开票人身份类别查询当前创客众包的每年收入
     *
     * @param invoicePeopleType
     * @param makerId
     * @return
     */
    R<IncomeYearVO> queryEveryYearCrowdIncome(InvoicePeopleType invoicePeopleType, Long makerId);

    /**
     * 根据开票人身份类别，年份查询每月收入
     *
     * @param invoicePeopleType
     * @param makerId
     * @param year
     * @return
     */
    R<YearTradeVO> queryEveryMonthCrowdIncome(InvoicePeopleType invoicePeopleType, Long makerId, Long year);

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
    R<IPage<AllIncomeYearMonthEnterpriseVO>> queryMakerToEnterpriseCrowdIncome(InvoicePeopleType invoicePeopleType, Long makerId, Long year, Long month, IPage<AllIncomeYearMonthEnterpriseVO> page);

    /**
     * 根据开票人身份类别，年份，月份，商户编号（可选）查询众包收入明细
     *
     * @param invoicePeopleType
     * @param id
     * @param year
     * @param month
     * @param enterpriseId
     * @param page
     * @return
     */
    R<IPage<IncomeDetailYearMonthVO>> queryCrowdIncomeDetail(InvoicePeopleType invoicePeopleType, Long id, Long year, Long month, Long enterpriseId, IPage<IncomeDetailYearMonthVO> page);

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
    R<BigDecimal> queryCrowdDetailAllIncome(InvoicePeopleType invoicePeopleType, Long makerId, Long year, Long month, Long enterpriseId);

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
}

