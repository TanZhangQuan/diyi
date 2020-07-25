package com.lgyun.system.user.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.vo.AgreementVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
public class AgreementWrapper extends BaseEntityWrapper<AgreementEntity, AgreementVO> {

    public static AgreementWrapper build() {
        return new AgreementWrapper();
    }

    @Override
    public AgreementVO entityVO(AgreementEntity agreement) {
        return BeanUtil.copy(agreement, AgreementVO.class);
    }

}
