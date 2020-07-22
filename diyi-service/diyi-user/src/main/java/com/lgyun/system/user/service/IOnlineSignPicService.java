package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.OnlineSignPicEntity;

import javax.servlet.http.HttpServletRequest;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-18 15:59:14
 */
public interface IOnlineSignPicService extends BaseService<OnlineSignPicEntity> {

    /**
     * 保存签名图片
     */
    R saveOnlineSignPic(Long ObjectID, ObjectType objectType, String signPic, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId);
}

