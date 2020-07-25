package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.vo.PayEnterpriseVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
public class PayEnterpriseWrapper extends BaseEntityWrapper<PayEnterpriseEntity, PayEnterpriseVO> {

    public static PayEnterpriseWrapper build() {
        return new PayEnterpriseWrapper();
    }

    @Override
    public PayEnterpriseVO entityVO(PayEnterpriseEntity enterprisePay) {

        if (enterprisePay == null){
            return null;
        }

        return BeanUtil.copy(enterprisePay, PayEnterpriseVO.class);
    }

}
