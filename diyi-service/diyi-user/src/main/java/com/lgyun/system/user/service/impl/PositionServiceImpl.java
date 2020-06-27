package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.PositionEntity;
import com.lgyun.system.user.mapper.PositionMapper;
import com.lgyun.system.user.service.IPositionService;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Service("positionService")
public class PositionServiceImpl extends ServiceImpl<PositionMapper, PositionEntity> implements IPositionService {

}
