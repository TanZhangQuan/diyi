package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.MakerEntity;
import com.lgyun.system.user.mapper.MakerMapper;
import com.lgyun.system.user.service.IMakerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MakerServiceImpl extends ServiceImpl<MakerMapper, MakerEntity> implements IMakerService {
    private Logger logger = LoggerFactory.getLogger(MakerServiceImpl.class);

    @Override
    public MakerEntity findByOpenid(String openid) {
        return baseMapper.findByOpenid(openid);
    }

    @Override
    public MakerEntity findByPhoneNumber(String phoneNumber) {
        return baseMapper.findByPhoneNumber(phoneNumber);
    }
}
