package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.vo.AgreementVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public class AgreementWrapper extends BaseEntityWrapper<AgreementEntity, AgreementVO>  {

    public static AgreementWrapper build() {
        return new AgreementWrapper();
    }

	@Override
	public AgreementVO entityVO(AgreementEntity agreement) {
			AgreementVO agreementVO = BeanUtil.copy(agreement, AgreementVO.class);
		return agreementVO;
	}

}
