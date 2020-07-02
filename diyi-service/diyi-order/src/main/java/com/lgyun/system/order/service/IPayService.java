package com.lgyun.system.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.system.order.entity.PayEntity;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-06-26 16:57:54
 */
public interface IPayService extends IService<PayEntity> {
    /**
     * 查询订单以抢的条数
     */
    Integer queryRobOrderCount(Long orderId);
}

