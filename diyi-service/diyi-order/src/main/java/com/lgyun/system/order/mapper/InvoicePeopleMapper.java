package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.entity.InvoicePeopleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
@Mapper
public interface InvoicePeopleMapper extends BaseMapper<InvoicePeopleEntity> {
    /**
     * 通过创客id查询开票人
     */
    List<InvoicePeopleEntity> findInvoicePeopleMakerId(Long makerId);
}

