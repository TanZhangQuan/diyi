package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.PartnerEntity;
import com.lgyun.system.user.vo.PartnerVO;

/**
 * 合伙人信息表包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
public class PartnerWrapper extends BaseEntityWrapper<PartnerEntity, PartnerVO> {

    public static PartnerWrapper build() {
        return new PartnerWrapper();
    }

    @Override
    public PartnerVO entityVO(PartnerEntity partner) {
        return BeanUtil.copy(partner, PartnerVO.class);
    }

}
