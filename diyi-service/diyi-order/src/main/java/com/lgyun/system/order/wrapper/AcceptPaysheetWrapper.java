package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.AcceptPaysheetEntity;
import com.lgyun.system.order.vo.AcceptPaysheetVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-17 14:38:25
 */
public class AcceptPaysheetWrapper extends BaseEntityWrapper<AcceptPaysheetEntity, AcceptPaysheetVO> {

    public static AcceptPaysheetWrapper build() {
        return new AcceptPaysheetWrapper();
    }

    @Override
    public AcceptPaysheetVO entityVO(AcceptPaysheetEntity acceptPaysheet) {
        return BeanUtil.copy(acceptPaysheet, AcceptPaysheetVO.class);
    }

}
