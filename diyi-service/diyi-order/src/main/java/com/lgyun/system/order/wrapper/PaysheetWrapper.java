package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.PaysheetEntity;
import com.lgyun.system.order.vo.PaysheetVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
public class PaysheetWrapper extends BaseEntityWrapper<PaysheetEntity, PaysheetVO> {

    public static PaysheetWrapper build() {
        return new PaysheetWrapper();
    }

    @Override
    public PaysheetVO entityVO(PaysheetEntity paysheet) {

        if (paysheet == null){
            return null;
        }

        return BeanUtil.copy(paysheet, PaysheetVO.class);
    }

}
