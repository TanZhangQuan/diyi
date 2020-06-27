package com.lgyun.system.user.service.impl;

import com.lgyun.system.user.service.IDeliverMaterialService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lgyun.system.user.mapper.DeliverMaterialMapper;
import com.lgyun.system.user.entity.DeliverMaterialEntity;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Service("deliverMaterialService")
public class DeliverMaterialServiceImpl extends ServiceImpl<DeliverMaterialMapper, DeliverMaterialEntity> implements IDeliverMaterialService {

}
