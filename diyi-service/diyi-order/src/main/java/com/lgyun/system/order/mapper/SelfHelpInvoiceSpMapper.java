package com.lgyun.system.order.mapper;

import com.lgyun.system.order.entity.SelfHelpInvoiceSpEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自助开票-服务商：记录自助开票主表的提交给不同服务商的 Mapper
 *
 * @author tzq
 * @since 2020-08-19 16:10:30
 */
@Mapper
public interface SelfHelpInvoiceSpMapper extends BaseMapper<SelfHelpInvoiceSpEntity> {

}

