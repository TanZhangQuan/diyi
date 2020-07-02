package com.lgyun.system.order.wrapper;


import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.PayEntity;
import com.lgyun.system.order.vo.PayVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-06-26 16:57:54
 */
public class PayWrapper extends BaseEntityWrapper<PayEntity, PayVO> {

    public static PayWrapper build() {
        return new PayWrapper();
    }

	@Override
	public PayVO entityVO(PayEntity pay) {
			PayVO payVO = BeanUtil.copy(pay, PayVO.class);

		return payVO;
	}

}
