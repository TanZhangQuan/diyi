package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.RunCompanyEntity;
import com.lgyun.system.user.mapper.RunCompanyMapper;
import com.lgyun.system.user.service.IRunCompanyService;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Service("runCompanyService")
public class RunCompanyServiceImpl extends ServiceImpl<RunCompanyMapper, RunCompanyEntity> implements IRunCompanyService {

}
