package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.SpringUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.service.IMakerService;
import com.lgyun.system.user.vo.IndividualBusinessVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
public class IndividualBusinessWrapper extends BaseEntityWrapper<IndividualBusinessEntity, IndividualBusinessVO> {

	private static IMakerService makerService;

	static {
		makerService = SpringUtil.getBean(IMakerService.class);
	}

	public static IndividualBusinessWrapper build() {
        return new IndividualBusinessWrapper();
    }

	@Override
	public IndividualBusinessVO entityVO(IndividualBusinessEntity individualBusiness) {
    	IndividualBusinessVO individualBusinessVO = BeanUtil.copy(individualBusiness, IndividualBusinessVO.class);
		String bizName = makerService.getName(individualBusiness.getMakerId());
		individualBusinessVO.setBizName(bizName);
		return individualBusinessVO;
	}

}
