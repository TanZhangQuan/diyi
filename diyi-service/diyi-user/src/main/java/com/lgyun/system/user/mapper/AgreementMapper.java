package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.SignType;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.vo.AgreementWebVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * Mapper
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Mapper
public interface AgreementMapper extends BaseMapper<AgreementEntity> {

    /**
     * 查询商户关联服务商的加盟合同
     */
    List<AgreementWebVO> selectServiceAgreement(Long enterpriseId, String serviceProviderName, String agreementNo, SignType signType,Integer agreementType, IPage<AgreementWebVO> page);


    List<AgreementWebVO> selectServiceSupplementaryAgreement(Long enterpriseId, String serviceProviderName, String agreementNo,Integer agreementType, IPage<AgreementWebVO> page);
}

