package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.mapper.EnterpriseMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, EnterpriseEntity> implements IEnterpriseService {
    private Logger logger = LoggerFactory.getLogger(EnterpriseServiceImpl.class);

}
