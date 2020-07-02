package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;
import com.lgyun.system.user.vo.IndividualEnterpriseVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
public class IndividualEnterpriseWrapper extends BaseEntityWrapper<IndividualEnterpriseEntity, IndividualEnterpriseVO> {

    public static IndividualEnterpriseWrapper build() {
        return new IndividualEnterpriseWrapper();
    }

	@Override
	public IndividualEnterpriseVO entityVO(IndividualEnterpriseEntity individualEnterprise) {
			IndividualEnterpriseVO individualEnterpriseVO = BeanUtil.copy(individualEnterprise, IndividualEnterpriseVO.class);

		return individualEnterpriseVO;
	}

}
