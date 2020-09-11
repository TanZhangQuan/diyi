package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.SignType;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.vo.AgreementMakerWebVO;
import com.lgyun.system.user.vo.AgreementServiceVO;
import com.lgyun.system.user.vo.AgreementWebVO;
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


    List<AgreementWebVO> selectServiceSupplementaryAgreement(Long enterpriseId, String serviceProviderName, String agreementNo,AgreementType agreementType, IPage<AgreementWebVO> page);

    List selectMakerAgreement( Long enterpriseId,IPage<AgreementMakerWebVO> page);

    /**
     * 商户和创客的补充协议
     */
    List<AgreementMakerWebVO> selectEnterpriseMakerAgreement( Long enterpriseId,IPage<AgreementMakerWebVO> page);

    /**
     *查询服务商加盟平台合同和承诺函
     */
    List<AgreementServiceVO> findSeriveAgreement(String agreementNo, Long serviceProviderId, IPage<AgreementServiceVO> page);

    /**
     * 查询创客加盟平台合同和承诺函
     */
    List<AgreementServiceVO> findMakerAgreement(String agreementNo, Long serviceProviderId,String makerName,IPage<AgreementServiceVO> page);

    /**
     *查询商户加盟平台合同和承诺函
     */
    List<AgreementServiceVO> findEnterpriseAgreement(String agreementNo, Long serviceProviderId,String enterpriseName,IPage<AgreementServiceVO> page);
}

