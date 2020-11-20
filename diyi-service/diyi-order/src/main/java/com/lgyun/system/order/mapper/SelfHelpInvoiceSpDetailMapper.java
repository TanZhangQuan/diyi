package com.lgyun.system.order.mapper;

import com.lgyun.system.order.entity.SelfHelpInvoiceSpDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 服务商开票明细：是从自助开票明细中选择过来的，信息是一致的 Mapper
 *
 * @author tzq
 * @since 2020-08-19 16:10:30
 */
@Mapper
public interface SelfHelpInvoiceSpDetailMapper extends BaseMapper<SelfHelpInvoiceSpDetailEntity> {

}

