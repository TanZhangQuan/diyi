package com.lgyun.system.order.mapper;

import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.vo.admin.SelfHelpInvoiceDetailAdminVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Mapper
public interface SelfHelpInvoiceDetailMapper extends BaseMapper<SelfHelpInvoiceDetailEntity> {

    List<SelfHelpInvoiceDetailAdminVO> getSelfHelpInvoiceIdAll(Long selfHelpInvoiceId);
}

