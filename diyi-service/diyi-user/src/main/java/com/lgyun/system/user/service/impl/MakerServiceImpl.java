package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.MakerMapper;
import com.lgyun.system.user.service.IMakerService;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Service("makerService")
public class MakerServiceImpl extends ServiceImpl<MakerMapper, MakerEntity> implements IMakerService {

}
