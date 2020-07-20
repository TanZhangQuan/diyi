package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.PayMakerEntity;
import com.lgyun.system.order.vo.PayMakerVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
public class PayMakerWrapper extends BaseEntityWrapper<PayMakerEntity, PayMakerVO> {

    public static PayMakerWrapper build() {
        return new PayMakerWrapper();
    }

    @Override
    public PayMakerVO entityVO(PayMakerEntity payMaker) {
        PayMakerVO payMakerVO = BeanUtil.copy(payMaker, PayMakerVO.class);
        return payMakerVO;
    }

}
