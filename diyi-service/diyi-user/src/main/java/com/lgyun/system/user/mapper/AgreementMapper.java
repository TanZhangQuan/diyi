package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * Mapper
 *
 * @author tzq
 * @since 2020-06-26 17:21:06
 */
@Mapper
public interface AgreementMapper extends BaseMapper<AgreementEntity> {

    /**
     * 查询商户关联服务商的加盟合同
     *
     * @param enterpriseId
     * @param serviceProviderName
     * @param agreementNo
     * @param agreementType
     * @param page
     * @return
     */
    List<AgreementWebVO> queryServiceProviderAgreement(Long enterpriseId, AgreementType agreementType, String serviceProviderName, String agreementNo, IPage<AgreementWebVO> page);

    /**
     * 查询创客加盟协议
     *
     * @param enterpriseId
     * @param page
     * @return
     */
    List<AgreementMakerWebVO> queryMakerAgreement(Long enterpriseId, IPage<AgreementMakerWebVO> page);

    /**
     * 商户和创客的补充协议
     *
     * @param enterpriseId
     * @param page
     * @return
     */
    List<EnterpriseMakerAgreementVO> queryEnterpriseMakerSupplementaryAgreement(Long enterpriseId, IPage<EnterpriseMakerAgreementVO> page);

    /**
     * 查询服务商加盟协议
     *
     * @param agreementNo
     * @param serviceProviderId
     * @return
     */
    List<AgreementServiceVO> findSeriveAgreement(Long serviceProviderId, String agreementNo);

    /**
     * 查询创客加盟协议
     *
     * @param agreementNo
     * @param serviceProviderId
     * @param makerName
     * @param page
     * @return
     */
    List<AgreementServiceVO> findMakerAgreement(Long serviceProviderId, String agreementNo, String makerName, IPage<AgreementServiceVO> page);

    /**
     * 查询商户加盟协议
     *
     * @param agreementNo
     * @param serviceProviderId
     * @param enterpriseName
     * @param page
     * @return
     */
    List<AgreementServiceVO> findEnterpriseAgreement(Long serviceProviderId, String agreementNo, String enterpriseName, IPage<AgreementServiceVO> page);

    /**
     * 服务商查询商户业务真实性承诺函
     *
     * @param agreementNo
     * @param serviceProviderId
     * @param enterpriseName
     * @param page
     * @return
     */
    List<AgreementServiceVO> findEnterprisePromise(Long serviceProviderId, String agreementNo, String enterpriseName, IPage<AgreementServiceVO> page);

    /**
     * 服务商查询服务商和商户的补充协议
     *
     * @param agreementNo
     * @param serviceProviderId
     * @param enterpriseName
     * @param page
     * @return
     */
    List<AgreementServiceVO> findEnterpriseSupplement(Long serviceProviderId, String agreementNo, String enterpriseName, IPage<AgreementServiceVO> page);

    /**
     * 查询创客合同的签署状态
     *
     * @param makerName
     * @param page
     * @return
     */
    List<AgreementMakerStateAdminVO> queryMakerAgreementState(String makerName, IPage<AgreementMakerStateAdminVO> page);

    /**
     * 通过创客id查询创客和商户的补充协议
     *
     * @param makerId
     * @param enterpriseId
     * @param page
     * @return
     */
    List<AgreementMakerEnterAdminVO> queryEnterpriseMakerSupplement(Long enterpriseId, Long makerId, IPage<AgreementMakerEnterAdminVO> page);

    /**
     * 查询商户合同的签署状态
     *
     * @param enterpriseName
     * @param page
     * @return
     */
    List<AgreementEnterpriseStateAdminVO> queryEnterpriseAgreementState(String enterpriseName, IPage<AgreementEnterpriseStateAdminVO> page);

    /**
     * 查询合作商户和服务商补充协议
     *
     * @param enterpriseId
     * @param serviceProviderId
     * @param page
     * @return
     */
    List<AgreementServiceVO> queryEnterpriseServiceProviderSupplement(Long serviceProviderId, Long enterpriseId, IPage<AgreementServiceVO> page);

    /**
     * 根据商户查询商户业务真实性承诺函
     *
     * @param enterpriseId
     * @param page
     * @return
     */
    List<AgreementServiceVO> queryEnterIdPromise(Long enterpriseId, IPage<AgreementServiceVO> page);

    /**
     * 查询服务商合同的签署状态
     *
     * @param serviceProviderName
     * @param page
     * @return
     */
    List<AgreementServiceStateAdminVO> queryServiceAgreementState(String serviceProviderName, IPage<AgreementServiceStateAdminVO> page);

    /**
     * 查询合同
     *
     * @param agreementId
     * @return
     */
    String queryOnlineAgreementUrl(Long agreementId);
}

