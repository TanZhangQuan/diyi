package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailsVO;
import com.lgyun.system.order.vo.SelfHelpInvoiceYearMonthMoneyVO;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper
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
    SelfHelpInvoiceYearMonthMoneyVO yearMonthMoney(Long businessEnterpriseId, MakerType makerType);

}

