package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.OnlineSignPicEntity;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-18 15:59:14
 */
public interface IOnlineSignPicService extends BaseService<OnlineSignPicEntity> {

    /**
     * 确认签字
     *
     * @param ObjectId
     * @param objectType
     * @param signPic
     * @param onlineAgreementTemplateId
     * @param onlineAgreementNeedSignId
     * @return
     */
    R<String> saveOnlineSignPic(Long ObjectId, ObjectType objectType, String signPic, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId);

}

