package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.AcceptPaysheetCsEntity;
import com.lgyun.system.order.vo.AcceptPaysheetCsVO;

/**
 * 众包交付支付验收单表包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-05 10:43:29
 */
public class AcceptPaysheetCsWrapper extends BaseEntityWrapper<AcceptPaysheetCsEntity, AcceptPaysheetCsVO> {

    public static AcceptPaysheetCsWrapper build() {
        return new AcceptPaysheetCsWrapper();
    }

    @Override
    public AcceptPaysheetCsVO entityVO(AcceptPaysheetCsEntity acceptPaysheetCs) {
        return BeanUtil.copy(acceptPaysheetCs, AcceptPaysheetCsVO.class);
    }

}
