package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.vo.IndividualBusinessVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public class IndividualBusinessWrapper extends BaseEntityWrapper<IndividualBusinessEntity, IndividualBusinessVO> {

    public static IndividualBusinessWrapper build() {
        return new IndividualBusinessWrapper();
    }

	@Override
	public IndividualBusinessVO entityVO(IndividualBusinessEntity individualBusiness) {
			IndividualBusinessVO individualBusinessVO = BeanUtil.copy(individualBusiness, IndividualBusinessVO.class);

		return individualBusinessVO;
	}

}
