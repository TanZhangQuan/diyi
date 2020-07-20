package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.PayEnterpriseEntity;
import com.lgyun.system.order.vo.PayEnterpriseVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
public class PayEnterpriseWrapper extends BaseEntityWrapper<PayEnterpriseEntity, PayEnterpriseVO> {

    public static PayEnterpriseWrapper build() {
        return new PayEnterpriseWrapper();
    }

    @Override
    public PayEnterpriseVO entityVO(PayEnterpriseEntity payEnterprise) {
        PayEnterpriseVO payEnterpriseVO = BeanUtil.copy(payEnterprise, PayEnterpriseVO.class);
        return payEnterpriseVO;
    }

}
