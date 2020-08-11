package com.lgyun.system.user.service;


import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.SignPower;
import com.lgyun.common.enumeration.TemplateType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.OnlineAgreementNeedSignEntity;
import com.lgyun.system.user.vo.OnlineAgreementNeedSignVO;

import java.util.List;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
public interface IOnlineAgreementNeedSignService extends BaseService<OnlineAgreementNeedSignEntity> {

    /**
     * 添加需要签署的授权协议
     *
     * @param onlineAgreementTemplateId
     * @param objectType
     * @param signPower
     * @param objectId
     */
    R<String> OnlineAgreementNeedSignAdd(Long onlineAgreementTemplateId, ObjectType objectType, SignPower signPower, Long objectId);

    /**
     * 根据模板，用户类型，用户查询需要签署的授权协议
     *
     * @param onlineAgreementTemplateId
     * @param objectType
     * @param signPower
     * @param objectId
     * @return
     */
    OnlineAgreementNeedSignEntity findByonlineAgreementTemplateIdAndobjectTypeAndobjectId(Long onlineAgreementTemplateId, ObjectType objectType, SignPower signPower, Long objectId);

    /**
     * 查询创客需要签署的授权协议
     *
     * @param makerId
     * @param templateType
     * @return
     */
    R<List<OnlineAgreementNeedSignVO>> getOnlineAgreementNeedSign(Long makerId, TemplateType templateType);

}

