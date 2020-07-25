package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.ChargeDetailEntity;
import com.lgyun.system.order.vo.ChargeDetailVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:07
 */
public class ChargeDetailWrapper extends BaseEntityWrapper<ChargeDetailEntity, ChargeDetailVO> {

    public static ChargeDetailWrapper build() {
        return new ChargeDetailWrapper();
    }

    @Override
    public ChargeDetailVO entityVO(ChargeDetailEntity chargeDetail) {

        if (chargeDetail == null){
            return null;
        }

        return BeanUtil.copy(chargeDetail, ChargeDetailVO.class);
    }

}
