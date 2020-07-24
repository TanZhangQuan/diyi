package com.lgyun.system.order.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.vo.SelfHelpInvoiceYearMonthMoneyVO;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface ISelfHelpInvoiceService extends BaseService<SelfHelpInvoiceEntity> {

    /**
     * 提交自助接口
     *
     * @param selfHelpInvoiceDto
     * @return
     */
    R submitSelfHelpInvoice(SelfHelpInvoiceDto selfHelpInvoiceDto);

    /**
     * 查询开票详情
     *
     * @param selfHelpInvoiceId
     * @return
     */
    R getSelfHelpInvoiceDetails(Long selfHelpInvoiceId);

    /**
     * 查询个体户月度开票金额和年度开票金额
     *
     * @param individualBusinessId
     * @param makerType
     * @return
     */
    R<SelfHelpInvoiceYearMonthMoneyVO> yearMonthMoney(Long individualBusinessId, MakerType makerType);

}

