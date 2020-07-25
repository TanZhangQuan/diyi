package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.OnlineAgreementTemplateEntity;
import com.lgyun.system.user.vo.OnlineAgreementTemplateVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
public class OnlineAgreementTemplateWrapper extends BaseEntityWrapper<OnlineAgreementTemplateEntity, OnlineAgreementTemplateVO> {

    public static OnlineAgreementTemplateWrapper build() {
        return new OnlineAgreementTemplateWrapper();
    }

    @Override
    public OnlineAgreementTemplateVO entityVO(OnlineAgreementTemplateEntity onlineAgreementTemplate) {
        return BeanUtil.copy(onlineAgreementTemplate, OnlineAgreementTemplateVO.class);
    }

}
