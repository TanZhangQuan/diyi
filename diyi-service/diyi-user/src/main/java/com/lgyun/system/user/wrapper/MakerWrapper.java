package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.vo.MakerVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public class MakerWrapper extends BaseEntityWrapper<MakerEntity, MakerVO> {

    public static MakerWrapper build() {
        return new MakerWrapper();
    }

	@Override
	public MakerVO entityVO(MakerEntity maker) {
			MakerVO makerVO = BeanUtil.copy(maker, MakerVO.class);

		return makerVO;
	}

}
