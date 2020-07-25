package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.vo.EnterpriseVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
public class EnterpriseWrapper extends BaseEntityWrapper<EnterpriseEntity, EnterpriseVO> {

    public static EnterpriseWrapper build() {
        return new EnterpriseWrapper();
    }

    @Override
    public EnterpriseVO entityVO(EnterpriseEntity enterprise) {
        return BeanUtil.copy(enterprise, EnterpriseVO.class);
    }

}
