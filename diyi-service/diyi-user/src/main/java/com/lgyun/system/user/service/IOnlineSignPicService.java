package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AuditState;
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
     * @param objectId
     * @param objectType
     * @param signPic
     * @param onlineAgreementTemplateId
     * @param onlineAgreementNeedSignId
     * @return
     */
    R<String> confirmationSignature(Long objectId, ObjectType objectType, String signPic, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId);



    R saveOnlineSignPic(Long objectId, ObjectType objectType,String signPic);


    /**
     *查看创客是否存在签名
     */
    R whetherAutograph(ObjectType objectType,Long objectId);


    /**
     * 一键授权
     */
    R<String> oneClickAuthorization(Long makerId,String signPic);

    /**
     * 解除一键授权
     */
    R<String> relieveOneClickAuthorization(Long makerId);

    /**
     * 审核一键授权
     * @param makerId
     * @param auditState
     * @param rejectedExplanation
     * @return
     */
    R toExamineAuthorization(Long makerId, AuditState auditState, String rejectedExplanation);


    /**
     * 自动创建商户-创客补充协议
     * @return
     */
    Integer saveMerchantMakerSupplement(Long enterpriseId, Long makerId);


    /**
     * 自动创建服务商-创客补充协议
     * @return
     */
    Integer saveServiceProviderMakerSupplement(Long serviceProviderId, Long makerId);
}

