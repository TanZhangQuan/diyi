package com.lgyun.system.order.mapper;

import com.lgyun.system.order.entity.InvoiceApplicationPayListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.vo.ApplicationVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 总包开票申请关联的支付清单 Mapper
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
@Mapper
public interface InvoiceApplicationPayListMapper extends BaseMapper<InvoiceApplicationPayListEntity> {

    List<ApplicationVo> findApplication(Long payEnterpriseId);
}

