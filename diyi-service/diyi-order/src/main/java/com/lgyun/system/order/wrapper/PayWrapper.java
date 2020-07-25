package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.PayEntity;
import com.lgyun.system.order.vo.PayVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:07
 */
public class PayWrapper extends BaseEntityWrapper<PayEntity, PayVO> {

    public static PayWrapper build() {
        return new PayWrapper();
    }

    @Override
    public PayVO entityVO(PayEntity pay) {

        if (pay == null){
            return null;
        }

        return BeanUtil.copy(pay, PayVO.class);
    }

}
