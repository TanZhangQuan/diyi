package com.lgyun.system.user.service.impl;

import com.lgyun.system.user.service.ISetupService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lgyun.system.user.mapper.SetupMapper;
import com.lgyun.system.user.entity.SetupEntity;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Service("setupService")
public class SetupServiceImpl extends ServiceImpl<SetupMapper, SetupEntity> implements ISetupService {

}
