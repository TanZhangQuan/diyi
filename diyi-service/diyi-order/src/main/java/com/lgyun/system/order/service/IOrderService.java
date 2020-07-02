package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.system.order.entity.OrderEntity;
import com.lgyun.system.order.vo.OrderVO;

import java.util.List;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-06-26 16:57:54
 */
public interface IOrderService extends IService<OrderEntity> {

    /**
     * 自定义分页
     *
     * @param page
     * @return
     */
    IPage<OrderVO> selectOrderPage(IPage page, String orderState);

    /**
     * 抢单
     */
    R robOrder(Long orderId, Long makerId);

}

