package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.SignType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.ServiceProviderEntity;
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
     */
    R<String> saveAuthorization(Long enterpriseId, String paperAgreementURL);

    /**
     * 查询商户关联服务商的加盟合同
     *
     * @param page
     * @param enterpriseId
     * @param serviceProviderName
     * @param agreementNo
     * @param agreementType
     * @return
     */
    IPage<AgreementWebVO> selectServiceAgreement(IPage<AgreementWebVO> page, Long enterpriseId, String serviceProviderName, String agreementNo, AgreementType agreementType);

    /**
     * 查询商户关联服务商的补充协议
     *
     * @param page
     * @param enterpriseId
     * @param serviceProviderName
     * @param agreementNo
     * @param agreementType
     * @return
     */
    IPage<AgreementWebVO> selectServiceSupplementaryAgreement(IPage<AgreementWebVO> page, Long enterpriseId, String serviceProviderName, String agreementNo, AgreementType agreementType);

    /**
     * 商户上传服务商的补充协议
     *
     * @param enterpriseId
     * @param paperAgreementURL
     * @param serviceProviderId
     * @return
     */
    R<String> saveSupplementaryAgreement(Long enterpriseId, String paperAgreementURL, Long serviceProviderId);

    /**
     * 查询创客加盟合同
     *
     * @param page
     * @param enterpriseId
     * @return
     */
    R<IPage<AgreementMakerWebVO>> selectMakerAgreement(IPage<AgreementMakerWebVO> page, Long enterpriseId);

    /**
     * 商户上传商户和创客的补充协议
     *
     * @param enterpriseId
     * @param paperAgreementURL
     * @param makerIds
     * @return
     */
    R saveEnterpriseMakerAgreement(Long enterpriseId, String paperAgreementURL, String makerIds);

    /**
     * 商户和创客的补充协议
     *
     * @param page
     * @param enterpriseId
     * @return
     */
    R selectEnterpriseMakerAgreement(IPage<AgreementMakerWebVO> page, Long enterpriseId);

    /**
     * 发布商户和创客的补充协议
     *
     * @param enterpriseId
     * @param paperAgreementURL
     * @param makerIds
     * @param agreementType
     * @return
     * @throws Exception
     */
    R saveEntMakAgreement(Long enterpriseId, String paperAgreementURL, String makerIds, AgreementType agreementType) throws Exception;


    /**
     * 查询服务商加盟平台合同
     */
    R findSeriveAgreement(String agreementNo, Long serviceProviderId);

    /**
     * 上传服务商和商户的补充协议
     */
    R uploadSupplement(String contractUrl, Long serviceProviderId, Long enterpriseId);

    /**
     * 查询创客加盟平台合同
     */
    R findMakerAgreement(String agreementNo, Long serviceProviderId, String makerName, IPage<AgreementServiceVO> page);

    /**
     * 查询商户加盟平台合同
     */
    R findEnterpriseAgreement(String agreementNo, Long serviceProviderId, String enterpriseName, IPage<AgreementServiceVO> page);

    /**
     * 服务商查询商户承诺函
     */
    R findEnterprisePromise(String agreementNo, Long serviceProviderId, String enterpriseName, IPage<AgreementServiceVO> page);

    /**
     * 服务商查询服务商和商户的补充协议
     */
    R findEnterpriseSupplement(String agreementNo, Long serviceProviderId, String enterpriseName, IPage<AgreementServiceVO> page);


    /**
     * 根据创客id查询加盟或者授权协议
     *
     * @param makerId
     * @param agreementType
     * @return
     */
    R<AgreementEntity> findAdminMakerId(Long makerId, AgreementType agreementType);

    /**
     * 平台端添加合同
     */
    R saveAdminAgreement(Long makerId, Long enterpriseId, Long serviceProviderId, Long objectId, ObjectType objectType, AgreementType agreementType, String paperAgreementUrl);

    /**
     * 平台根据商户id查询商户加盟合同、授权协议或价格协议
     *
     * @param enterpriseId
     * @param agreementType
     * @return
     */
    R queryAdminEnterpriseId(Long enterpriseId, AgreementType agreementType);

    /**
     * 平台根据服务商id查询服务商加盟合同
     */
    R findAdminSerIdAgreement(Long serviceProviderId, AgreementType agreementType);

    /**
     * 查看在线协议URL
     *
     * @param agreementId
     * @return
     */
    R queryOnlineAgreementUrl(Long agreementId);


    /**
     * 平台查询创客合同的签署状态
     */
    R queryMakerAgreementState(String makerName, IPage<AgreementMakerStateAdminVO> page);

    /**
     * 平台通过创客id查询创客和商户的补充协议
     */
    R queryMakerIdSupplement(Long makerId, IPage<AgreementMakerEnterAdminVO> page);

    /**
     * 平台查询商户合同的签署状态
     */
    R queryEnterpriseAgreementState(String enterpriseName, IPage<AgreementEnterpriseStateAdminVO> page);


    /**
     * 平台根据商户id查询商户和创客的补充协议
     */
    R queryEnterpriseIdSupplement(Long enterpriseId, IPage<AgreementMakerEnterAdminVO> page);

    /**
     * 平台根据商户id查询合作商户和服务商补充协议
     */
    R queryEnterIdServiceSupplement(Long enterpriseId, IPage<AgreementServiceVO> page);

    /**
     * 平台根据商户id查询商户承诺函
     */
    R queryEnterIdPromise(Long enterpriseId, IPage<AgreementServiceVO> page);

    /**
     * 平台查询服务商合同的签署状态
     */
    R queryServiceAgreementState(String serviceProviderName, IPage<AgreementServiceStateAdminVO> page);

    /**
     * 平台根据服务商id查询合作服务商和商户补充协议
     */
    R queryServiceIdEnterSupplement(Long serviceProviderId, IPage<AgreementServiceVO> page);

    /**
     * 根据合同id修改合同路径
     */
    R saveAdminAgreementId(Long agreementId, String agreementUrl);

    /**
     * 平台查所有商户
     */
    R queryAdminEnterpriseAll(Long enterpriseId, String enterpriseName, IPage<EnterpriseEntity> page);

    /**
     * 平台查所有服务商
     */
    R queryAdminServiceAll(Long serviceProviderId, String serviceProviderName, IPage<ServiceProviderEntity> page);

    /**
     * 查询已签署已审核通过的商户-创客补充协议
     *
     * @param makerId
     * @param enterpriseId
     * @return
     */
    int queryEntMakSupplementaryAgreementNum(Long makerId, Long enterpriseId);

    /**
     * 查询已签署已审核通过的商户-创客补充协议
     * @param makerId
     * @param enterpriseId
     * @return
     */
    AgreementEntity findByEnterpriseAndMakerSuppl(Long makerId, Long enterpriseId);

}

