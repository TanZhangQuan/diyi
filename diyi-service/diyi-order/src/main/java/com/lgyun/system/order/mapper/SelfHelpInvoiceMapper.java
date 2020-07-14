package com.lgyun.system.order.mapper;

import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailsVO;
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
     */
    SelfHelpInvoiceDetailsVO getSelfHelpInvoiceDetails(Long selfHelpInvoiceId);
}

