package com.lgyun.system.order.wrapper;


import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.OrderEntity;
import com.lgyun.system.order.vo.OrderVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-06-26 16:57:54
 */
public class OrderWrapper extends BaseEntityWrapper<OrderEntity, OrderVO> {

    public static OrderWrapper build() {
        return new OrderWrapper();
    }

	@Override
	public OrderVO entityVO(OrderEntity order) {
			OrderVO orderVO = BeanUtil.copy(order, OrderVO.class);

		return orderVO;
	}
}
