package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.mapper.AgreementMapper;
import com.lgyun.system.user.service.IAgreementService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Service
@AllArgsConstructor
public class AgreementServiceImpl extends ServiceImpl<AgreementMapper, AgreementEntity> implements IAgreementService {
    private static Logger logger = LoggerFactory.getLogger(AgreementServiceImpl.class);

    @Override
    public List<AgreementEntity> makerIdFind(Long makerId) {
        List<AgreementEntity> agreementEntities = baseMapper.makerIdFind(makerId);
        return agreementEntities;
    }

    @Override
    public List<AgreementEntity> makerIdCompanyFind(Long makerId, Long employeeId) {
        List<AgreementEntity> agreementEntities = baseMapper.makerIdCompanyFind(makerId,employeeId);
        return agreementEntities;
    }
}
