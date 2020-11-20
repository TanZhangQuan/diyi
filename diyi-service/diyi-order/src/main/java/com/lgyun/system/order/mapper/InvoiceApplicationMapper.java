package com.lgyun.system.order.mapper;

import com.lgyun.system.order.entity.InvoiceApplicationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 开票申请：记录商户的总包开票申请 Mapper
 *
 * @author tzq
 * @since 2020-08-11 16:00:00
 */
@Mapper
public interface InvoiceApplicationMapper extends BaseMapper<InvoiceApplicationEntity> {

}

