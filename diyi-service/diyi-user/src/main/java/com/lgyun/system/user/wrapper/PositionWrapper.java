package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.PositionEntity;
import com.lgyun.system.user.vo.PositionVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public class PositionWrapper extends BaseEntityWrapper<PositionEntity, PositionVO> {

    public static PositionWrapper build() {
        return new PositionWrapper();
    }

	@Override
	public PositionVO entityVO(PositionEntity position) {
			PositionVO positionVO = BeanUtil.copy(position, PositionVO.class);

		return positionVO;
	}

}
