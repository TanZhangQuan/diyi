package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.order.entity.SelfHelpInvoicePersonEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Mapper
public interface SelfHelpInvoicePersonMapper extends BaseMapper<SelfHelpInvoicePersonEntity> {

    /**
     * 根据创客Idc查询自助开票非创客开票人
     *
     * @param page
     * @param makerId
     * @return
     */
    List<SelfHelpInvoicePersonEntity> findPersonMakerId(IPage page, Long makerId);
}

