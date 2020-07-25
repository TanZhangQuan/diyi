package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.OrderEntity;
import com.lgyun.system.order.vo.OrderVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:07
 */
public class OrderWrapper extends BaseEntityWrapper<OrderEntity, OrderVO> {

    public static OrderWrapper build() {
        return new OrderWrapper();
    }

    @Override
    public OrderVO entityVO(OrderEntity order) {

        if (order == null){
            return null;
        }

        return BeanUtil.copy(order, OrderVO.class);
    }

}
