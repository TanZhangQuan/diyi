package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.IndividualEnterpriseAnnualFeeEntity;
import com.lgyun.system.user.vo.IndividualEnterpriseAnnualFeeVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
public class IndividualEnterpriseAnnualFeeWrapper extends BaseEntityWrapper<IndividualEnterpriseAnnualFeeEntity, IndividualEnterpriseAnnualFeeVO> {

    public static IndividualEnterpriseAnnualFeeWrapper build() {
        return new IndividualEnterpriseAnnualFeeWrapper();
    }

	@Override
	public IndividualEnterpriseAnnualFeeVO entityVO(IndividualEnterpriseAnnualFeeEntity individualEnterpriseAnnualFee) {
			IndividualEnterpriseAnnualFeeVO individualEnterpriseAnnualFeeVO = BeanUtil.copy(individualEnterpriseAnnualFee, IndividualEnterpriseAnnualFeeVO.class);

		return individualEnterpriseAnnualFeeVO;
	}

}
