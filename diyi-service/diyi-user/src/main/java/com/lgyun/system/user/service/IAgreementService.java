package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
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
     * 根据条件查询成功的合同
     *
     * @param enterpriseId
     * @param agreementType
     * @param auditState
     * @param signState
     * @return
     */
    AgreementEntity findSuccessAgreement(Long enterpriseId, AgreementType agreementType, AuditState auditState, SignState signState);

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
    List<AgreementEntity> findByEnterpriseId(Long enterpriseId);

    /**
     * 根据商户和合同类型找合同
     */
    AgreementWebVO findByEnterpriseAndType(Long enterpriseId, AgreementType agreementType);

    /**
     * 根据商户查询商户的单方授权函
     */
    R selectAuthorization(Long enterpriseId, IPage<AgreementEntity> page);

    /**
     * 商户上传授权函
     */
    R<String> saveAuthorization(Long enterpriseId, String paperAgreementURL);

    /**
     * 查询商户关联服务商的加盟合同
     */
    IPage<AgreementWebVO> selectServiceAgreement(IPage<AgreementWebVO> page, Long enterpriseId, String serviceProviderName, String agreementNo, SignType signType, AgreementType agreementType);

    /**
     * 查询商户关联服务商的补充协议
     */
    IPage<AgreementWebVO> selectServiceSupplementaryAgreement(IPage<AgreementWebVO> page, Long enterpriseId, String serviceProviderName, String agreementNo, AgreementType agreementType);

    /**
     * 商户上传服务商的补充协议
     */
    R<String> saveSupplementaryAgreement(Long enterpriseId, String paperAgreementURL, Long serviceProviderId);

    /**
     * 查询创客加盟合同
     */
    R<IPage<AgreementMakerWebVO>> selectMakerAgreement(IPage<AgreementMakerWebVO> page, Long enterpriseId);

    /**
     * 商户上传商户和创客的补充协议
     */
    R saveEnterpriseMakerAgreement(Long enterpriseId, String paperAgreementURL);

    /**
     * 商户和创客的补充协议
     */
    R selectEnterpriseMakerAgreement(IPage<AgreementMakerWebVO> page, Long enterpriseId);

    /**
     *
     */
    R saveOnlineAgreement(Long enterpriseId, String paperAgreementURL, Boolean boolAllMakers, String makerIds, Integer templateCount, AgreementType agreementType, IMakerEnterpriseService makerEnterpriseService) throws Exception;


    /**
     * 查询服务商加盟平台合同和承诺函
     */
    R findSeriveAgreement(String agreementNo, Long serviceProviderId, IPage<AgreementServiceVO> page);

    /**
     * 上传加盟合同和承诺函
     */
    R uploadContractAndLetter(String contractUrl, String letterUrl, Long serviceProviderId);

    /**
     * 查询创客加盟平台合同和承诺函
     */
    R findMakerAgreement(String agreementNo, Long serviceProviderId, String makerName, IPage<AgreementServiceVO> page);

    /**
     * 查询商户加盟平台合同和承诺函
     */
    R findEnterpriseAgreement(String agreementNo, Long serviceProviderId ,String enterpriseName,IPage<AgreementServiceVO> page);

    /**
     * 根据创客id查询加盟或者授权协议
     */
    R<AgreementEntity> findAdminMakerId(Long makerId,AgreementType agreementType);

    /**
     *平台通过创客id查询合作商户的合同
     */
    R findAdMaEnterAgreement(Long makerId,String enterpriseName,IPage<AgreementMakerEnterAdminVO> page);

    /**
     * 平台端添加合同
     */
    R saveAdminAgreement(Long agreementId,String name,Long objectId, ObjectType objectType,Integer contractType,AgreementType agreementType,String paperAgreementUrl);

    /**
     * 平台根据商户id查询商户加盟合同或授权协议
     */
    R findAdminEnterpriseId(Long enterpriseId,AgreementType agreementType);

    /**
     * 平台根据商户id查询合作服务商的合同
     */
    R findEnterIdServiceAgreement(Long enterpriseId,String serviceProviderName,IPage<AgreementEnterServiceAdminVO> page);

    /**
     * 平台根据服务商id查询服务商加盟合同或授权协议
     */
    R findAdminSerIdAgreement(Long serviceProviderId,AgreementType agreementType);

    /**
     * 上传合同
     *
     * @param objectType
     * @param objectId
     * @param agreementType
     * @param file
     * @return
     */
    void uploadAgreementByAdmin(ObjectType objectType, Long objectId, AgreementType agreementType, String file);
}

