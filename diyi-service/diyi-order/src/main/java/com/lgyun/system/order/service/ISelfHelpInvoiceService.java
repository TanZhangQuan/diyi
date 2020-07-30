package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailsVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;

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
    R<String> submitSelfHelpInvoice(SelfHelpInvoiceDto selfHelpInvoiceDto);

    /**
     * 查询开票详情
     *
     * @param selfHelpInvoiceId
     * @return
     */
    R<SelfHelpInvoiceDetailsVO> getSelfHelpInvoiceDetails(Long selfHelpInvoiceId);

    /**
     * 查询月度开票金额和年度开票金额
     *
     * @param businessEnterpriseId
     * @param makerType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long businessEnterpriseId, MakerType makerType);

    /**
     * 查询开票次数，月度开票金额，年度开票金额和总开票金额
     *
     * @param businessEnterpriseId
     * @param makerType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> selfHelpInvoiceStatistics(Long businessEnterpriseId, MakerType makerType);

    /**
     * 查询自助开票记录
     *
     * @param page
     * @param businessEnterpriseId
     * @param makerType
     * @return
     */
    R<IPage<SelfHelpInvoiceListVO>> selfHelpInvoiceList(IPage<SelfHelpInvoiceListVO> page, Long businessEnterpriseId, MakerType makerType);
}

