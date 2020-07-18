package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.EnterprisePayEntity;
import com.lgyun.system.order.vo.EnterprisePayVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
public class EnterprisePayWrapper extends BaseEntityWrapper<EnterprisePayEntity, EnterprisePayVO> {

    public static EnterprisePayWrapper build() {
        return new EnterprisePayWrapper();
    }

    @Override
    public EnterprisePayVO entityVO(EnterprisePayEntity enterprisePay) {
        EnterprisePayVO enterprisePayVO = BeanUtil.copy(enterprisePay, EnterprisePayVO.class);
        return enterprisePayVO;
    }

}
