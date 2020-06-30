package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.mapper.AgreementMapper;
import com.lgyun.system.user.service.IAgreementService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AgreementServiceImpl extends ServiceImpl<AgreementMapper, AgreementEntity> implements IAgreementService {
    private Logger logger = LoggerFactory.getLogger(AgreementServiceImpl.class);

}
