package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.PartnerEnterpriseEntity;
import com.lgyun.system.user.vo.PartnerEnterpriseVO;

/**
 * 合伙人-商户关联表包装类,返回视图层所需的字段
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
public class PartnerEnterpriseWrapper extends BaseEntityWrapper<PartnerEnterpriseEntity, PartnerEnterpriseVO> {

    public static PartnerEnterpriseWrapper build() {
        return new PartnerEnterpriseWrapper();
    }

    @Override
    public PartnerEnterpriseVO entityVO(PartnerEnterpriseEntity partnerEnterprise) {
        return BeanUtil.copy(partnerEnterprise, PartnerEnterpriseVO.class);
    }

}
