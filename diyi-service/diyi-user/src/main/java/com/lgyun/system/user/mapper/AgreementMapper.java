package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.SignType;
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
     */
    List<AgreementWebVO> selectServiceAgreement(Long enterpriseId, String serviceProviderName, String agreementNo, SignType signType, AgreementType agreementType, IPage<AgreementWebVO> page);


    List<AgreementWebVO> selectServiceSupplementaryAgreement(Long enterpriseId, String serviceProviderName, String agreementNo, AgreementType agreementType, IPage<AgreementWebVO> page);

    List selectMakerAgreement(Long enterpriseId, IPage<AgreementMakerWebVO> page);

    /**
     * 商户和创客的补充协议
     */
    List<AgreementMakerWebVO> selectEnterpriseMakerAgreement(Long enterpriseId, IPage<AgreementMakerWebVO> page);

    /**
     * 查询服务商加盟平台合同
     */
    List<AgreementServiceVO> findSeriveAgreement(String agreementNo, Long serviceProviderId);

    /**
     * 查询创客加盟平台合同
     */
    List<AgreementServiceVO> findMakerAgreement(String agreementNo, Long serviceProviderId, String makerName, IPage<AgreementServiceVO> page);

    /**
     * 查询商户加盟平台合同
     */
    List<AgreementServiceVO> findEnterpriseAgreement(String agreementNo, Long serviceProviderId, String enterpriseName, IPage<AgreementServiceVO> page);

    /**
     * 服务商查询商户承诺函
     */
    List<AgreementServiceVO> findEnterprisePromise(String agreementNo, Long serviceProviderId, String enterpriseName, IPage<AgreementServiceVO> page);

    /**
     * 服务商查询服务商和商户的补充协议
     */
    List<AgreementServiceVO> findEnterpriseSupplement(String agreementNo, Long serviceProviderId, String enterpriseName, IPage<AgreementServiceVO> page);

    /**
     * 查询平台自然人创客的合同
     */
    List<AgreementMakerEnterAdminVO> findAdMaEnterAgreement(Long makerId, String enterpriseName, IPage<AgreementMakerEnterAdminVO> page);

    /**
     * 平台根据商户id查询合作服务商的合同
     */
    List<AgreementEnterServiceAdminVO> findEnterIdServiceAgreement(Long enterpriseId, String serviceProviderName, IPage<AgreementEnterServiceAdminVO> page);


    /**
     * 平台查询创客合同的签署状态
     */
    List<AgreementMakerStateAdminVO> queryMakerAgreementState(String makerName, IPage<AgreementMakerStateAdminVO> page);

    /**
     * 平台通过创客id查询创客和商户的补充协议
     */
    List<AgreementMakerEnterAdminVO> queryMakerIdSupplement(Long makerId, Long enterpriseId, IPage<AgreementMakerEnterAdminVO> page);

    /**
     * 平台查询商户合同的签署状态
     */
    List<AgreementMakerStateAdminVO> queryEnterpriseAgreementState(String enterpriseName, IPage<AgreementMakerStateAdminVO> page);

    /**
     * 平台根据商户id查询合作商户和服务商补充协议
     */
    List<AgreementServiceVO> queryEnterIdServiceSupplement(Long enterpriseId, Long serviceProviderId, IPage<AgreementServiceVO> page);

    /**
     * 平台根据商户id查询商户承诺函
     */
    List<AgreementServiceVO> queryEnterIdPromise(Long enterpriseId, IPage<AgreementServiceVO> page);

    /**
     * 平台查询服务商合同的签署状态
     */
    List<AgreementServiceStateAdminVO> queryServiceAgreementState(IPage<AgreementServiceStateAdminVO> page);

    /**
     * 删除商户合同
     *
     * @param enterpriseId
     * @param agreementType
     */
    void deleteByEnterprise(Long enterpriseId, AgreementType agreementType);

}

