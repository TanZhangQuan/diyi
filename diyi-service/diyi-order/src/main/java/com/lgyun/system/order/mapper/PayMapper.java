package com.lgyun.system.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.order.entity.PayEntity;

/**
 *  Mapper
 *
 * @author jun
 * @since 2020-06-26 16:57:54
 */
public interface PayMapper extends BaseMapper<PayEntity> {
    /**
     * 查询订单以抢的条数
     */
    Integer queryRobOrderCount(Long orderId);

}

