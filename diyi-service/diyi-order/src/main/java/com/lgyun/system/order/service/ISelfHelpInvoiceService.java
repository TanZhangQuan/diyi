package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.InvoicePeopleType;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.SelfHelpInvoicePayDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.vo.PayEnterpriseListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailsVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;

import java.util.Map;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface ISelfHelpInvoiceService extends BaseService<SelfHelpInvoiceEntity> {

    /**
     * 查询开票详情
     *
     * @param selfHelpInvoiceId
     * @return
     */
    R<Map> getSelfHelpInvoiceDetails(Long selfHelpInvoiceId);

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
    R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long individualBusinessId, InvoicePeopleType invoicePeopleType);


    /**
     * 根据商户ID, 开票人身份类别查询自助开票
     *
     * @param page
     * @param enterpriseId
     * @return
     */
    R findMakerTypeSelfHelpInvoice(IPage<SelfHelpInvoiceDetailsVO> page, Long enterpriseId, MakerType makerType,String invoicePeopleName,String startTime,String endTime);

    /**
     * 查询当前商户所有自主开票记录(众包)
     *
     * @param enterpriseId
     * @param selfHelpInvoicePayDto
     * @param page
     * @return
     */
    R<IPage<PayEnterpriseListVO>> getSelfHelfInvoiceByEnterpriseId(Long enterpriseId, SelfHelpInvoicePayDto selfHelpInvoicePayDto, IPage<PayEnterpriseListVO> page);
}

