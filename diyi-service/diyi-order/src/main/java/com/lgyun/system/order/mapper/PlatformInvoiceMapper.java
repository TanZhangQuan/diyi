package com.lgyun.system.order.mapper;

import com.lgyun.system.order.entity.PlatformInvoiceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 总包发票信息：记录服务商开具给商户的总包发票，一次开票可能多个清单一起 Mapper
 *
 * @author tzq
 * @since 2020-08-11 16:00:00
 */
@Mapper
public interface PlatformInvoiceMapper extends BaseMapper<PlatformInvoiceEntity> {

}

