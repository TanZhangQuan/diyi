package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.PositionEntity;
import com.lgyun.system.user.mapper.PositionMapper;
import com.lgyun.system.user.service.IPositionService;
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
public class PositionServiceImpl extends ServiceImpl<PositionMapper, PositionEntity> implements IPositionService {
    private Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);

}
