package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.IndividualBusinessAnnualFeeEntity;
import com.lgyun.system.user.vo.IndividualBusinessAnnualFeeVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020-07-02 17:44:02
 */
public class IndividualBusinessAnnualFeeWrapper extends BaseEntityWrapper<IndividualBusinessAnnualFeeEntity, IndividualBusinessAnnualFeeVO> {

    public static IndividualBusinessAnnualFeeWrapper build() {
        return new IndividualBusinessAnnualFeeWrapper();
    }

    @Override
    public IndividualBusinessAnnualFeeVO entityVO(IndividualBusinessAnnualFeeEntity individualBusinessAnnualFee) {

        if (individualBusinessAnnualFee == null){
            return null;
        }

        return BeanUtil.copy(individualBusinessAnnualFee, IndividualBusinessAnnualFeeVO.class);
    }

}
