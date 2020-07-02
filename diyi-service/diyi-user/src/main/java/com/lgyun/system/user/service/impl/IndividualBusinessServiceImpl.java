package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.user.entity.IndividualBusinessEntity;
import com.lgyun.system.user.mapper.IndividualBusinessMapper;
import com.lgyun.system.user.service.IIndividualBusinessService;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
@Service("individualBusinessService")
public class IndividualBusinessServiceImpl extends ServiceImpl<IndividualBusinessMapper, IndividualBusinessEntity> implements IIndividualBusinessService {

}
