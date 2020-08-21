package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.SignType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.vo.AgreementMakerWebVO;
import com.lgyun.system.user.vo.AgreementWebVO;

import java.util.List;
import java.util.Map;

/**
 * Service 接口
 *
 * @author liangfeihu
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
    List<AgreementEntity> findByEnterpriseId(Long enterpriseId);

    /**
     * 根据商户和合同类型找合同
     */
    AgreementWebVO findByEnterpriseAndType(Long enterpriseId, AgreementType agreementType);

    /**
     * 根据商户查询商户的单方授权函
     */
    R selectAuthorization(Long enterpriseId,IPage<AgreementEntity> page);

    /**
     * 商户上传授权函
     */
    R<String> saveAuthorization(Long enterpriseId,String paperAgreementURL);

    /**
     * 查询商户关联服务商的加盟合同
     */
    IPage<AgreementWebVO> selectServiceAgreement(IPage<AgreementWebVO> page, Long enterpriseId, String serviceProviderName, String agreementNo,SignType signType,AgreementType agreementType);

    /**
     * 查询商户关联服务商的补充协议
     */
    IPage<AgreementWebVO> selectServiceSupplementaryAgreement(IPage<AgreementWebVO> page, Long enterpriseId, String serviceProviderName, String agreementNo,AgreementType agreementType);

    /**
     * 商户上传服务商的补充协议
     */
    R<String> saveSupplementaryAgreement(Long enterpriseId,String paperAgreementURL,Long serviceProviderId);

    /**
     *查询创客加盟合同
     */
    R<IPage<AgreementMakerWebVO>> selectMakerAgreement(IPage<AgreementMakerWebVO> page,Long enterpriseId);

    /**
     * 商户上传商户和创客的补充协议
     */
    R saveEnterpriseMakerAgreement(Long enterpriseId, String paperAgreementURL);

    /**
     * 商户和创客的补充协议
     */
    R selectEnterpriseMakerAgreement(IPage<AgreementMakerWebVO> page,Long enterpriseId);

    /**
     *
     */
    R saveOnlineAgreement(Long enterpriseId, String paperAgreementURL,Boolean boolAllMakers,String makerIds,Integer templateCount,AgreementType agreementType,IMakerEnterpriseService makerEnterpriseService) throws Exception;
}

