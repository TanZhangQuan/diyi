package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.PayListDto;
import com.lgyun.system.order.dto.SelfHelpInvoiceDto;
import com.lgyun.system.order.dto.SelfHelpInvoiceWebDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.vo.PayListVO;
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
    R getSelfHelpInvoiceDetails(Long selfHelpInvoiceId);

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


    /**
     * 查询月度开票金额和年度开票金额
     *
     * @param makerType
     * @return
     */
    R<SelfHelpInvoiceStatisticsVO> yearMonthMoney(Long individualBusinessId, MakerType makerType);


    /**
     * 根据创客类型查询自助开票
     */
    R findMakerTypeSelfHelpInvoice(IPage<SelfHelpInvoiceDetailsVO> page,Long enterpriseId,MakerType makerType);

    /**
     *商户提交自助开票
     */
    R submitWebSelfHelpInvoice(SelfHelpInvoiceWebDto selfHelpInvoiceWebDto);


    /**
     * 查询当前商户所有自主开票记录(众包)
     *
     * @param enterpriseId
     * @param payListDto
     * @param page
     * @return
     */
    R<IPage<PayListVO>> getByDtoEnterprise(Long enterpriseId, PayListDto payListDto, IPage<PayListVO> page);
}

