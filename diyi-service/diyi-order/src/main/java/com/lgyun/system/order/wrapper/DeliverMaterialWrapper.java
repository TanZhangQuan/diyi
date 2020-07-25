package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.DeliverMaterialEntity;
import com.lgyun.system.order.vo.DeliverMaterialVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
public class DeliverMaterialWrapper extends BaseEntityWrapper<DeliverMaterialEntity, DeliverMaterialVO> {

    public static DeliverMaterialWrapper build() {
        return new DeliverMaterialWrapper();
    }

    @Override
    public DeliverMaterialVO entityVO(DeliverMaterialEntity deliverMaterial) {
        return BeanUtil.copy(deliverMaterial, DeliverMaterialVO.class);
    }

}
