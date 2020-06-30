package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.IndividualBusinessAnnualFeeEntity;
import com.lgyun.system.user.vo.IndividualBusinessAnnualFeeVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public class IndividualBusinessAnnualFeeWrapper extends BaseEntityWrapper<IndividualBusinessAnnualFeeEntity, IndividualBusinessAnnualFeeVO> {

    public static IndividualBusinessAnnualFeeWrapper build() {
        return new IndividualBusinessAnnualFeeWrapper();
    }

    @Override
    public IndividualBusinessAnnualFeeVO entityVO(IndividualBusinessAnnualFeeEntity individualBusinessAnnualFee) {
        IndividualBusinessAnnualFeeVO individualBusinessAnnualFeeVO = BeanUtil.copy(individualBusinessAnnualFee, IndividualBusinessAnnualFeeVO.class);
        return individualBusinessAnnualFeeVO;
    }

}
