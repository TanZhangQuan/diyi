package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.SetupEntity;
import com.lgyun.system.user.mapper.SetupMapper;
import com.lgyun.system.user.service.ISetupService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Service
@AllArgsConstructor
public class SetupServiceImpl extends ServiceImpl<SetupMapper, SetupEntity> implements ISetupService {
    private static Logger logger = LoggerFactory.getLogger(SetupServiceImpl.class);

}
