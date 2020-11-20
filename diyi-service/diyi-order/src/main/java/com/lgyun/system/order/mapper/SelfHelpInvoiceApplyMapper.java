package com.lgyun.system.order.mapper;

import com.lgyun.system.order.entity.SelfHelpInvoiceApplyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自助开票申请：记录自助开票主表的申请记录情况 Mapper
 *
 * @author tzq
 * @since 2020-08-08 10:36:37
 */
@Mapper
public interface SelfHelpInvoiceApplyMapper extends BaseMapper<SelfHelpInvoiceApplyEntity> {

}

