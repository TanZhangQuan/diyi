package com.lgyun.system.user.service;

import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.OnlineAgreementTemplateEntity;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
public interface IOnlineAgreementTemplateService extends BaseService<OnlineAgreementTemplateEntity> {

    /**
     * 通过协议类别查询
     */
    OnlineAgreementTemplateEntity findTemplateType(AgreementType agreementType, Boolean boolAllSign);

    /**
     * 商户、服务商查询模板
     */
    OnlineAgreementTemplateEntity findEntSerTemplateType(Long objectId, ObjectType objectType, AgreementType agreementType);

}

