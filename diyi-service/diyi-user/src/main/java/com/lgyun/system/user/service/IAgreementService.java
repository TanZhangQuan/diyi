package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.SignType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.vo.*;

import java.util.List;
import java.util.Map;

/**
 * Service 接口
 *
 * @author tzq
 * @since 2020-06-26 17:21:06
 */
public interface IAgreementService extends BaseService<AgreementEntity> {

    /**
     * 根据创客找合同
     *
     * @param makerId
     * @param onlineAgreementTemplateId
     * @param onlineAgreementNeedSignId
     * @return
     */
    R<Map> makerIdFind(Long makerId, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId);

    /**
     * 根据商户找合同
     *
     * @param enterpriseId
     * @return
     */
    List<AgreementEntity> findByEnterpriseId(Long enterpriseId, Long makerId);

    /**
     * 根据商户和合同类型找合同
     *
     * @param enterpriseId
     * @param agreementType
     * @return
     */
    AgreementWebVO findByEnterpriseAndType(Long enterpriseId, AgreementType agreementType, SignType signType);

    /**
     * 根据商户查询商户的承诺函
     */
    R selectAuthorization(Long enterpriseId, IPage<AgreementEntity> page);

    /**
     * 商户上传承诺函
     *
     * @param enterpriseId
     * @param agreementUrl
     * @return
     */
    R<String> saveAuthorization(Long enterpriseId, String agreementUrl);

    /**
     * 查询商户关联服务商的加盟合同
     *
     * @param page
     * @param enterpriseId
     * @param serviceProviderName
     * @param agreementNo
     * @return
     */
    R<IPage<AgreementWebVO>> queryServiceProviderAgreement(Long enterpriseId, AgreementType agreementType, String serviceProviderName, String agreementNo, IPage<AgreementWebVO> page);

    /**
     * 商户上传服务商的补充协议
     *
     * @param enterpriseId
     * @param agreementUrl
     * @param serviceProviderId
     * @return
     */
    R<String> saveSupplementaryAgreement(Long enterpriseId, String agreementUrl, Long serviceProviderId);

    /**
     * 查询创客加盟协议
     *
     * @param page
     * @param enterpriseId
     * @return
     */
    R<IPage<AgreementMakerWebVO>> queryMakerAgreement(Long enterpriseId, IPage<AgreementMakerWebVO> page);

    /**
     * 商户上传商户和创客的补充协议
     *
     * @param enterpriseId
     * @param agreementUrl
     * @param makerIds
     * @return
     */
    R<String> saveEnterpriseMakerAgreement(Long enterpriseId, String agreementUrl, String makerIds);

    /**
     * 商户和创客的补充协议
     *
     * @param page
     * @param enterpriseId
     * @return
     */
    R<IPage<EnterpriseMakerAgreementVO>> queryEnterpriseMakerSupplementaryAgreement(Long enterpriseId, IPage<EnterpriseMakerAgreementVO> page);

    /**
     * 发布商户和创客的补充协议
     *
     * @param enterpriseId
     * @param agreementUrl
     * @param makerIds
     * @param agreementType
     * @return
     * @throws Exception
     */
    R<String> saveEntMakAgreement(Long enterpriseId, String agreementUrl, String makerIds, AgreementType agreementType);

    /**
     * 查询服务商加盟协议
     *
     * @param serviceProviderId
     * @param agreementNo
     * @return
     */
    R findSeriveAgreement(Long serviceProviderId, String agreementNo);

    /**
     * 上传服务商和商户的补充协议
     */
    R uploadSupplement(String contractUrl, Long serviceProviderId, Long enterpriseId);

    /**
     * 查询创客加盟协议
     *
     * @param serviceProviderId
     * @param agreementNo
     * @param makerName
     * @param page
     * @return
     */
    R findMakerAgreement(Long serviceProviderId, String agreementNo, String makerName, IPage<AgreementServiceVO> page);

    /**
     * 查询商户加盟协议
     *
     * @param serviceProviderId
     * @param agreementNo
     * @param enterpriseName
     * @param page
     * @return
     */
    R findEnterpriseAgreement(Long serviceProviderId, String agreementNo, String enterpriseName, IPage<AgreementServiceVO> page);

    /**
     * 服务商查询商户业务真实性承诺函
     *
     * @param serviceProviderId
     * @param agreementNo
     * @param enterpriseName
     * @param page
     * @return
     */
    R findEnterprisePromise(Long serviceProviderId, String agreementNo, String enterpriseName, IPage<AgreementServiceVO> page);

    /**
     * 服务商查询服务商和商户的补充协议
     *
     * @param serviceProviderId
     * @param agreementNo
     * @param enterpriseName
     * @param page
     * @return
     */
    R findEnterpriseSupplement(Long serviceProviderId, String agreementNo, String enterpriseName, IPage<AgreementServiceVO> page);


    /**
     * 根据创客id查询加盟或者授权协议
     *
     * @param makerId
     * @param agreementType
     * @return
     */
    R<AgreementEntity> findAdminMakerId(Long makerId, AgreementType agreementType);

    /**
     * 端添加合同
     *
     * @param makerId
     * @param enterpriseId
     * @param serviceProviderId
     * @param objectId
     * @param objectType
     * @param agreementType
     * @param agreementUrl
     * @return
     */
    R<String> saveAdminAgreement(Long makerId, Long enterpriseId, Long serviceProviderId, Long objectId, ObjectType objectType, AgreementType agreementType, String agreementUrl);

    /**
     * 根据商户查询商户加盟协议、授权协议或价格协议
     *
     * @param enterpriseId
     * @param agreementType
     * @return
     */
    R queryAdminEnterpriseId(Long enterpriseId, AgreementType agreementType);

    /**
     * 根据服务商查询服务商加盟协议
     *
     * @param serviceProviderId
     * @param agreementType
     * @return
     */
    R findAdminSerIdAgreement(Long serviceProviderId, AgreementType agreementType);

    /**
     * 查看在线协议URL
     *
     * @param agreementId
     * @return
     */
    R<String> queryOnlineAgreementUrl(Long agreementId);

    /**
     * 查询创客合同的签署状态
     *
     * @param makerName
     * @param page
     * @return
     */
    R queryMakerAgreementState(String makerName, IPage<AgreementMakerStateAdminVO> page);

    /**
     * 查询创客和商户的补充协议
     *
     * @param enterpriseId
     * @param makerId
     * @param page
     * @return
     */
    R<IPage<AgreementMakerEnterAdminVO>> queryEnterpriseMakerSupplement(Long enterpriseId, Long makerId, IPage<AgreementMakerEnterAdminVO> page);

    /**
     * 查询商户合同的签署状态
     *
     * @param enterpriseName
     * @param page
     * @return
     */
    R<IPage<AgreementEnterpriseStateAdminVO>> queryEnterpriseAgreementState(String enterpriseName, IPage<AgreementEnterpriseStateAdminVO> page);

    /**
     * 根据商户id查询合作商户和服务商补充协议
     *
     * @param serviceProviderId
     * @param enterpriseId
     * @param page
     * @return
     */
    R<IPage<AgreementServiceVO>> queryEnterpriseServiceProviderSupplement(Long serviceProviderId, Long enterpriseId, IPage<AgreementServiceVO> page);

    /**
     * 根据商户查询商户业务真实性承诺函
     *
     * @param enterpriseId
     * @param page
     * @return
     */
    R<IPage<AgreementServiceVO>> queryEnterIdPromise(Long enterpriseId, IPage<AgreementServiceVO> page);

    /**
     * 查询服务商合同的签署状态
     *
     * @param serviceProviderName
     * @param page
     * @return
     */
    R<IPage<AgreementServiceStateAdminVO>> queryServiceAgreementState(String serviceProviderName, IPage<AgreementServiceStateAdminVO> page);

    /**
     * 根据合同id修改合同路径
     *
     * @param agreementId
     * @param agreementUrl
     * @return
     */
    R<String> saveAdminAgreementId(Long agreementId, String agreementUrl);

    /**
     * 查询有效协议合同数
     *
     * @param partyA
     * @param partyAId
     * @param partyB
     * @param partyBId
     * @param agreementType
     * @return
     */
    int queryValidAgreementNum(ObjectType partyA, Long partyAId, ObjectType partyB, Long partyBId, AgreementType agreementType);

    /**
     * 查询已签署已审核通过的商户-创客补充协议
     * @param makerId
     * @param enterpriseId
     * @return
     */
    AgreementEntity findByEnterpriseAndMakerSuppl(Long makerId, Long enterpriseId);


    /**
     * 根据服务商查询合同
     * @param serviceProviderId
     * @param makerId
     * @return
     */
    List<AgreementEntity> findByserviceProviderId(Long serviceProviderId, Long makerId);

    /**
     *上传补充协议模板
     */
    R uploadSupplementAgreementTemplate(Long objectId,ObjectType objectType,String agreementUrl,AgreementType agreementType);


    /**
     * 查询商户、服务商创客补充协议模板
     */
    R querySupplementAgreement(Long objectId,ObjectType objectType,AgreementType agreementType);


    /**
     *查询合同模板
     */
    R queryContractTemplate();

    /**
     * 查询已签署已审核通过的服务商-创客补充协议
     * @param makerId
     * @param serviceProviderId
     * @return
     */
    AgreementEntity findByServiceProviderAndMakerSuppl(Long makerId, Long serviceProviderId);

    /**
     * 平台根据服务商id查询合作服务商和创客补充协议
     */
    R<IPage<AgreementServiceVO>> queryServiceProviderToMakerSupplementList(Long serviceProviderId, IPage<AgreementServiceVO> page);


    /**
     * 根据创客id查询加盟或者授权协议
     *
     * @param makerId
     * @param agreementType
     * @return
     */
    AgreementEntity findAdminMakerId1(Long makerId, AgreementType agreementType);

}

