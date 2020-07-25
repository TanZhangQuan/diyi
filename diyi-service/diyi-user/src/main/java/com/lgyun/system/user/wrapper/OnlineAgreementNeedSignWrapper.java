package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.OnlineAgreementNeedSignEntity;
import com.lgyun.system.user.vo.OnlineAgreementNeedSignVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
public class OnlineAgreementNeedSignWrapper extends BaseEntityWrapper<OnlineAgreementNeedSignEntity, OnlineAgreementNeedSignVO> {

    public static OnlineAgreementNeedSignWrapper build() {
        return new OnlineAgreementNeedSignWrapper();
    }

    @Override
    public OnlineAgreementNeedSignVO entityVO(OnlineAgreementNeedSignEntity onlineAgreementNeedSign) {
        return BeanUtil.copy(onlineAgreementNeedSign, OnlineAgreementNeedSignVO.class);
    }

}
