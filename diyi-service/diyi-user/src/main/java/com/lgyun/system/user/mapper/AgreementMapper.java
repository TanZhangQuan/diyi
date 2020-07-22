package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.entity.AgreementEntity;
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
     * 根据创客找合同
     */
    AgreementEntity makerIdFind(Long makerId,Long onlineAgreementTemplateId);


    /**
     * 根据创客和商户找合同
     */
    List<AgreementEntity> makerIdCompanyFind(Long employeeId);
}

