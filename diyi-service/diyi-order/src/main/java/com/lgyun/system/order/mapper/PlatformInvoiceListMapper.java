package com.lgyun.system.order.mapper;

import com.lgyun.system.order.entity.PlatformInvoiceListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 记录服务商开具给商户的总包发票 Mapper
 *
 * @author tzq
 * @since 2020-08-11 14:25:28
 */
@Mapper
public interface PlatformInvoiceListMapper extends BaseMapper<PlatformInvoiceListEntity> {

}

