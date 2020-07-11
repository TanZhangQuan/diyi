package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.PositionEntity;
import com.lgyun.system.user.mapper.PositionMapper;
import com.lgyun.system.user.service.IPositionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Service
@AllArgsConstructor
public class PositionServiceImpl extends ServiceImpl<PositionMapper, PositionEntity> implements IPositionService {
    private static Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);

}
