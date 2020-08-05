package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.system.order.dto.PayListDto;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.vo.PayListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailsVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceListVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceStatisticsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Mapper
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Mapper
public interface SelfHelpInvoiceMapper extends BaseMapper<SelfHelpInvoiceEntity> {

    /**
     * 查询开票详情
     *
     * @param selfHelpInvoiceId
     * @return
     */
    SelfHelpInvoiceDetailsVO getSelfHelpInvoiceDetails(Long selfHelpInvoiceId);

    /**
     * 查询个体户月度开票金额和年度开票金额
     *
     * @param businessEnterpriseId
     * @param makerType
     * @return
     */
    SelfHelpInvoiceStatisticsVO yearMonthMoney(Long businessEnterpriseId, MakerType makerType);

    /**
     * 查询个体户月度开票金额和年度开票金额
     *
     * @param businessEnterpriseId
     * @param makerType
     * @return
     */
    SelfHelpInvoiceStatisticsVO selfHelpInvoiceStatistics(Long businessEnterpriseId, MakerType makerType);

    /**
     * 查询个独开票记录
     *
     * @param businessEnterpriseId
     * @param page
     * @return
     */
    List<SelfHelpInvoiceListVO> selfHelpInvoiceListEnterprise(Long businessEnterpriseId, IPage<SelfHelpInvoiceListVO> page);

    /**
     * 查询个体户开票记录
     *
     * @param businessEnterpriseId
     * @param page
     * @return
     */
    List<SelfHelpInvoiceListVO> selfHelpInvoiceListBusiness(Long businessEnterpriseId, IPage<SelfHelpInvoiceListVO> page);

    /**
     * 根据创客类型查询自助开票
     */
    List<SelfHelpInvoiceDetailsVO> findMakerTypeSelfHelpInvoice( Long enterpriseId, MakerType makerType,IPage<SelfHelpInvoiceDetailsVO> page);

    /**
     * 查询当前商户所有自主开票记录(众包)
     *
     * @param enterpriseId
     * @param payListDto
     * @param page
     * @return
     */
    List<PayListVO> getByDtoEnterprise(Long enterpriseId, PayListDto payListDto, IPage<PayListVO> page);
}

