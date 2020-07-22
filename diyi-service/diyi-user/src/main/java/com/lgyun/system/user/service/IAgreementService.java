package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.AgreementEntity;

import java.util.List;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public interface IAgreementService extends BaseService<AgreementEntity> {
    /**
     * 根据创客找合同
     */
    R makerIdFind(Long makerId, Long onlineAgreementTemplateId,Long onlineAgreementNeedSignId);

    /**
     * 根据创客和商户找合同
     */
    List<AgreementEntity> makerIdCompanyFind(Long employeeId);
}

