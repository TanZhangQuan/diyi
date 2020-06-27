package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.DeliverMaterialEntity;
import com.lgyun.system.user.vo.DeliverMaterialVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
public class DeliverMaterialWrapper extends BaseEntityWrapper<DeliverMaterialEntity, DeliverMaterialVO> {

    public static DeliverMaterialWrapper build() {
        return new DeliverMaterialWrapper();
    }

	@Override
	public DeliverMaterialVO entityVO(DeliverMaterialEntity deliverMaterial) {
			DeliverMaterialVO deliverMaterialVO = BeanUtil.copy(deliverMaterial, DeliverMaterialVO.class);

		return deliverMaterialVO;
	}

}
